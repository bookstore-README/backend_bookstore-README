package com.bookstore.readme.domain.book.service.page;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.SortType;
import com.bookstore.readme.domain.book.dto.page.BookDto;
import com.bookstore.readme.domain.book.dto.page.BookPageDto;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.book.repository.BookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SingleSortPageService extends BookPage {
    private final BookRepository bookRepository;

    @Transactional
    public BookPageDto pageBooks(Integer cursorId, Integer limit, SortType sortType, boolean ascending) {
        PageRequest pageRequest = PageRequest.of(0, limit + 1, getSort(sortType, ascending));

        Book book = null;
        if (cursorId != null) {
            book = bookRepository.findById(cursorId.longValue())
                    .orElseThrow(() -> new NotFoundBookByIdException(cursorId.longValue()));
        }

        Page<Book> pageBooks = (book == null) ? bookRepository.findAll(pageRequest) : bookRepository.findAll(BookSpecification.singleSortPagination(book, sortType, ascending), pageRequest);
        List<Book> contents = pageBooks.getContent();
        List<BookDto> results = contents.stream()
                .map(BookDto::of)
                .limit(limit)
                .toList();

        int nextCursorId = pageBooks.hasNext() ? contents.get(contents.size() - 1).getId().intValue() : -1;
        return BookPageDto.builder()
                .total(results.size())
                .limit(limit)
                .cursorId(nextCursorId)
                .books(results)
                .build();
    }

    public BookPageDto pageBooks(Integer cursorId, Integer limit, SortType sortType, boolean ascending, String... categories) {
        PageRequest pageRequest = PageRequest.of(0, limit + 1, getSort(sortType, ascending));

        StringBuilder fullCategory = new StringBuilder();
        for (String category : categories) {
            fullCategory.append(category).append(",");
        }
        fullCategory.deleteCharAt(fullCategory.length() - 1);

        Book book = null;
        if (cursorId != null) {
            book = bookRepository.findById(cursorId.longValue())
                    .orElseThrow(() -> new NotFoundBookByIdException(cursorId.longValue()));
        }

        Page<Book> pageBooks = (book == null) ? bookRepository.findAllByCategoriesStartingWith(fullCategory.toString(), pageRequest) : bookRepository.findAll(BookSpecification.singleSortAndCategoryPagination(book, sortType, ascending, fullCategory.toString()), pageRequest);
        List<Book> contents = pageBooks.getContent();
        List<BookDto> results = contents.stream()
                .map(BookDto::of)
                .limit(limit)
                .toList();

        int nextCursorId = pageBooks.hasNext() ? contents.get(contents.size() - 1).getId().intValue() : -1;
        return BookPageDto.builder()
                .total(results.size())
                .limit(limit)
                .cursorId(nextCursorId)
                .books(results)
                .build();
    }
}
