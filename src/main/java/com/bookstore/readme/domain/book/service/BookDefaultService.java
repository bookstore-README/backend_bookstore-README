package com.bookstore.readme.domain.book.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.BookDto;
import com.bookstore.readme.domain.book.dto.BookListDto;
import com.bookstore.readme.domain.book.request.BookRequest;
import com.bookstore.readme.domain.book.response.BookResponse;
import com.bookstore.readme.domain.review.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
                .reviews(convertBookDtos)
                .build();

        return BookResponse.ok(data);
    }

    @Override
    @Transactional
    public BookResponse bookList(Integer bookId, Integer limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        Page<Book> pageBooks = bookQueryService.scrollSearch(bookId, pageRequest);

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

        int cursorId = pageBooks.hasNext() ? bookId + limit : -1;
        BookListDto data = BookListDto.builder()
                .total((int) pageBooks.getTotalElements())
                .limit(limit)
                .page(pageBooks.getTotalPages())
                .cursorId(cursorId)
                .reviews(convertBootDtos)
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
