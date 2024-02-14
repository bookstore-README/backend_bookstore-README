package com.bookstore.readme.domain.book.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.search.BookDto;
import com.bookstore.readme.domain.book.dto.search.BookSearchDetailDto;
import com.bookstore.readme.domain.book.dto.search.BookSearchReviewDto;
import com.bookstore.readme.domain.book.dto.search.BookSearchDto;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.bookmark.domain.Bookmark;
import com.bookstore.readme.domain.bookmark.dto.BookmarkDto;
import com.bookstore.readme.domain.review.domain.Review;
import com.bookstore.readme.domain.review.dto.ReviewSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookSearchService {
    private final BookRepository bookRepository;

    @Transactional
    public BookDto searchBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundBookByIdException(bookId));
        return BookDto.of(book);
    }

    @Transactional
    public BookSearchDto searchBookAndBookmark(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundBookByIdException(bookId));
        List<Bookmark> bookmarks = book.getBookmarks();
        List<BookmarkDto> bookmarkDto = bookmarks.stream()
                .filter(Bookmark::getIsMarked)
                .map(bookmark -> BookmarkDto.builder()
                        .bookId(bookmark.getBook().getId())
                        .memberId(bookmark.getMember().getId())
                        .isMarked(bookmark.getIsMarked())
                        .build())
                .toList();

        return BookSearchDto.of(book, bookmarkDto);
    }

    @Transactional
    public BookSearchReviewDto searchBookAndReview(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundBookByIdException(bookId));

        List<Review> reviews = book.getReviews();
        List<ReviewSearchDto> convertReview = reviews.stream()
                .map(ReviewSearchDto::of)
                .toList();

        return BookSearchReviewDto.of(book, convertReview);
    }

    @Transactional
    public BookSearchDetailDto searchBookDetail(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundBookByIdException(bookId));

        List<Bookmark> bookmarks = book.getBookmarks();
        List<BookmarkDto> bookmarkDto = bookmarks.stream()
                .filter(Bookmark::getIsMarked)
                .map(bookmark -> BookmarkDto.builder()
                        .bookmarkId(bookmark.getId())
                        .bookId(bookmark.getBook().getId())
                        .memberId(bookmark.getMember().getId())
                        .isMarked(bookmark.getIsMarked())
                        .build())
                .toList();

        List<Review> reviews = book.getReviews();
        List<ReviewSearchDto> convertReview = reviews.stream()
                .map(ReviewSearchDto::of)
                .toList();

        return BookSearchDetailDto.of(book, convertReview, bookmarkDto);
    }

}
