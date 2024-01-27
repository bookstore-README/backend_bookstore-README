package com.bookstore.readme.domain.book.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.BookDto;
import com.bookstore.readme.domain.book.dto.BookListDto;
import com.bookstore.readme.domain.book.dto.SortType;
import com.bookstore.readme.domain.book.request.BookPageRequest;
import com.bookstore.readme.domain.book.request.BookRequest;
import com.bookstore.readme.domain.book.response.BookResponse;
import com.bookstore.readme.domain.review.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class BookDefaultService implements BookService {
    private final BookQueryService bookQueryService;

    @Override
    @Transactional
    public BookResponse bookList() {
        List<Book> books = bookQueryService.findAll();
        List<BookDto> convertBookDtos = books.stream()
                .map(book -> {
                    BookDto convertBook = BookDto.toBookDto(book);
                    List<ReviewDto> convertReview = book.getReviews().stream()
                            .map(ReviewDto::toReviewDto)
                            .toList();

                    convertBook.getReviews().addAll(convertReview);
                    return convertBook;
                })
                .toList();

        BookListDto data = BookListDto.builder()
                .total(convertBookDtos.size())
                .limit(convertBookDtos.size())
                .page(1)
                .cursorId(-1)
                .books(convertBookDtos)
                .build();

        return BookResponse.ok(data);
    }

    @Override
    @Transactional
    public BookResponse bookList(BookPageRequest request) {
        if (request.getBookId() != null && request.getBookId() == -1) {
            return null;
        }

        Long count = bookQueryService.count();
        Integer cursorId = request.getBookId();
        if (request.getBookId() == null)
            cursorId = request.getAscending() ? 1 : count.intValue();

        Integer limit = request.getLimit();
        PageRequest pageRequest = PageRequest.of(0, limit, request.convertSort());
        Page<Book> pageBooks = bookQueryService.scrollSearch(cursorId, pageRequest, request.getAscending());

        List<Book> books = pageBooks.getContent();
        List<BookDto> convertBootDtos = books.stream()
                .map(book -> {
                    BookDto convertBook = BookDto.toBookDto(book);
                    List<ReviewDto> convertReview = book.getReviews().stream()
                            .map(ReviewDto::toReviewDto)
                            .toList();

                    convertBook.getReviews().addAll(convertReview);
                    return convertBook;
                })
                .toList();


        int nextCursorId = books.get(books.size() - 1).getId().intValue();
        nextCursorId = request.getAscending() ? nextCursorId + 1 : nextCursorId - 1;

        if (!pageBooks.hasNext())
            nextCursorId = -1;

        BookListDto data = BookListDto.builder()
                .total((int) pageBooks.getTotalElements())
                .limit(request.getLimit())
                .page(pageBooks.getTotalPages())
                .cursorId(nextCursorId)
                .books(convertBootDtos)
                .build();

        return BookResponse.ok(data);
    }

    @Override
    public BookResponse bookSave(BookRequest request) {
        Book book = request.toBook();
        bookQueryService.save(book);
        return BookResponse.ok(true);
    }
}
