package com.bookstore.readme.domain.book.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.SortType;
import com.bookstore.readme.domain.book.dto.page.BookDto;
import com.bookstore.readme.domain.book.dto.page.BookPageDto;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.book.repository.BookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class BookPageService {
    private final BookRepository bookRepository;

    @Transactional
    public BookPageDto bookList(Integer cursorId, Integer limit, List<SortType> sortTypes, boolean ascending) {
        PageRequest pageRequest = PageRequest.of(0, limit + 1, getSort(sortTypes, ascending));
        Page<Book> pageBooks = getPagination(cursorId, sortTypes.get(0), ascending, pageRequest);
        List<BookDto> convertBooks = getConvertBooks(pageBooks.getContent());
        int nextCursorId = nextCursorId(pageBooks.hasNext(), convertBooks);
        return getBookPageDto((int) bookRepository.count(), limit, nextCursorId, convertBooks);
    }

    @Transactional
    public BookPageDto bookList(Integer cursorId, Integer limit, List<SortType> sortTypes, boolean ascending, String... categories) {
        StringBuilder fullCategory = new StringBuilder();
        for (String category : categories) {
            fullCategory.append(category).append(",");
        }
        fullCategory.deleteCharAt(fullCategory.length() - 1);


        PageRequest pageRequest = PageRequest.of(0, limit + 1, getSort(sortTypes, ascending));
        Page<Book> pageBooks = getPagination(cursorId, sortTypes.get(0), ascending, pageRequest, fullCategory.toString());
        List<BookDto> convertBooks = getConvertBooks(pageBooks.getContent());
        int nextCursorId = nextCursorId(pageBooks.hasNext(), convertBooks);
        return getBookPageDto((int) bookRepository.count(), limit, nextCursorId, convertBooks);
    }

    private Page<Book> getPagination(Integer cursorId, SortType sortType, boolean ascending, PageRequest pageRequest, String category) {
        Page<Book> pageBooks;

        if (cursorId == null) {
            pageBooks = bookRepository.findAllByCategoriesStartingWith(category, pageRequest);
        } else {
            Book book = bookRepository.findById(cursorId.longValue())
                    .orElseThrow(() -> new NotFoundBookByIdException(cursorId.longValue()));
            Specification<Book> bookQuery = BookSpecification.pagination(book, sortType, ascending, category);
            pageBooks = bookRepository.findAll(bookQuery, pageRequest);
        }
        return pageBooks;
    }

    private Page<Book> getPagination(Integer cursorId, SortType sortType, boolean ascending, PageRequest pageRequest) {
        Page<Book> pageBooks;

        if (cursorId == null) {
            pageBooks = bookRepository.findAll(pageRequest);
        } else {
            Book book = bookRepository.findById(cursorId.longValue())
                    .orElseThrow(() -> new NotFoundBookByIdException(cursorId.longValue()));
            Specification<Book> bookQuery = BookSpecification.pagination(book, sortType, ascending);
            pageBooks = bookRepository.findAll(bookQuery, pageRequest);
        }
        return pageBooks;
    }

    private static List<BookDto> getConvertBooks(List<Book> books) {
        return books.stream()
                .map(BookDto::of)
                .collect(Collectors.toList());
    }

    private BookPageDto getBookPageDto(int total, int limit, Integer cursorId, List<BookDto> data) {
        return BookPageDto.builder()
                .total(total)
                .limit(limit)
                .cursorId(cursorId)
                .books(data)
                .build();
    }

    private Sort getSort(List<SortType> sortTypes, boolean ascending) {
        List<Sort.Order> collect = sortTypes.stream()
                .map(SortType::getSortType)
                .map(sort -> ascending ? Sort.Order.asc(sort) : Sort.Order.desc(sort))
                .toList();

        return Sort.by(collect);
    }

    private int nextCursorId(boolean hasNext, List<BookDto> convertBooks) {
        if (!hasNext)
            return -1;

        BookDto book = convertBooks.get(convertBooks.size() - 1);
        convertBooks.remove(convertBooks.size() - 1);

        return book.getBookId().intValue();
    }
}
