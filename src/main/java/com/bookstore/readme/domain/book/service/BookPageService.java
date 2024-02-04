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
        if (sortTypes.isEmpty() || !sortTypes.contains(SortType.ID))
            sortTypes.add(SortType.ID);

        PageRequest pageRequest = PageRequest.of(0, limit + 1, getSort(sortTypes, ascending));
        Page<Book> pageBooks;

        if (cursorId == null) {
            pageBooks = bookRepository.findAll(pageRequest);
        } else {
            Book book = bookRepository.findById(cursorId.longValue())
                    .orElseThrow(() -> new NotFoundBookByIdException(cursorId.longValue()));
            Specification<Book> bookQuery = BookSpecification.pagination(book, sortTypes, ascending);
            pageBooks = bookRepository.findAll(bookQuery, pageRequest);
        }

        List<Book> books = pageBooks.getContent();
        List<BookDto> convertBooks = books.stream()
                .map(BookDto::of)
                .collect(Collectors.toList());

        int nextCursorId = nextCursorId(pageBooks.hasNext(), convertBooks);

        return BookPageDto.builder()
                .total((int) bookRepository.count())
                .limit(limit)
                .cursorId(nextCursorId)
                .books(convertBooks)
                .build();

    }

    private Sort getSort(List<SortType> sortTypes, boolean ascending) {
        List<Sort.Order> collect = sortTypes.stream()
                .map(sortType -> ascending ? Sort.Order.asc(sortType.getSortType()) : Sort.Order.desc(sortType.getSortType()))
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
