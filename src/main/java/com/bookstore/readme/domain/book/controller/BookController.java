package com.bookstore.readme.domain.book.controller;

import com.bookstore.readme.domain.book.dto.search.BookDto;
import com.bookstore.readme.domain.book.dto.search.BookSearchDetailDto;
import com.bookstore.readme.domain.book.dto.search.BookSearchDto;
import com.bookstore.readme.domain.book.dto.search.BookSearchReviewDto;
import com.bookstore.readme.domain.book.request.BookPageRequest;
import com.bookstore.readme.domain.book.request.BookRequest;
import com.bookstore.readme.domain.book.response.BookResponse;
//import com.bookstore.readme.domain.book.service.BookPageService;
import com.bookstore.readme.domain.book.service.BookPageService;
import com.bookstore.readme.domain.book.service.BookSaveService;
import com.bookstore.readme.domain.book.service.BookSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "도서 API")
public class BookController {
    private final BookSaveService bookSaveService;
    private final BookSearchService bookSearchService;
    private final BookPageService bookPageService;

    @GetMapping("/book/{bookId}")
    @Operation(summary = "도서 단일 조회", description = "도서 아이디로 단일 조회하는 API")
    public ResponseEntity<BookResponse> bookSearch(
            @Parameter(description = "도서를 조회할 아이디", required = true)
            @PathVariable(name = "bookId") Integer bookId
    ) {
        BookDto bookDto = bookSearchService.searchBook(bookId.longValue());
        return ResponseEntity.ok(BookResponse.ok(bookDto));
    }

    @GetMapping("/book/{bookId}/review")
    @Operation(summary = "도서 단일 조회(리뷰 포함)", description = "도서 아이디로 단일 조회(리뷰 포함)하는 API")
    public ResponseEntity<BookResponse> searchBookAndReview(
            @Parameter(description = "도서를 조회할 아이디", required = true)
            @PathVariable(name = "bookId") Integer bookId
    ) {
        BookSearchReviewDto bookSearchReviewDto = bookSearchService.searchBookAndReview(bookId.longValue());
        return ResponseEntity.ok(BookResponse.ok(bookSearchReviewDto));
    }

    @GetMapping("/book/{bookId}/bookmark")
    @Operation(summary = "도서 단일 조회(북마크 포함)", description = "도서 아이디로 단일 조회하는 API")
    public ResponseEntity<BookResponse> searchBookAndBookmark(
            @Parameter(description = "도서를 조회할 아이디", required = true)
            @PathVariable(name = "bookId") Integer bookId
    ) {
        BookSearchDto bookSearchDto = bookSearchService.searchBookAndBookmark(bookId.longValue());
        return ResponseEntity.ok(BookResponse.ok(bookSearchDto));
    }

    @GetMapping("/book/{bookId}/detail")
    @Operation(summary = "도서 단일 조회(리뷰 ,북마크 포함)", description = "도서 아이디로 단일 조회하는 API")
    public ResponseEntity<BookResponse> searchBookDetail(
            @Parameter(description = "도서를 조회할 아이디", required = true)
            @PathVariable(name = "bookId") Integer bookId
    ) {
        BookSearchDetailDto bookSearchDetailDto = bookSearchService.searchBookDetail(bookId.longValue());
        return ResponseEntity.ok(BookResponse.ok(bookSearchDetailDto));
    }

    @GetMapping("/book")
    @Operation(summary = "도서 전체 조회(커서 기반)", description = "무한 스크롤 기능을 위한 API")
    public ResponseEntity<BookResponse> bookPage(@ParameterObject @Valid BookPageRequest request) {
        return ResponseEntity.ok()
                .body(BookResponse.ok(bookPageService.bookList(
                        request.getBookId(),
                        request.getLimit(),
                        request.getSort(),
                        request.getAscending())));
    }

    @PostMapping("/book")
    @Operation(summary = "도서 저장", description = "도서를 저장하기 위한 API")
    public ResponseEntity<BookResponse> bookSave(@RequestBody BookRequest request) {
        return ResponseEntity.ok(bookSaveService.bookSave(request));
    }
}
