package com.bookstore.readme.domain.book.controller;

import com.bookstore.readme.domain.book.dto.search.BookDto;
import com.bookstore.readme.domain.book.dto.search.BookSearchDetailDto;
import com.bookstore.readme.domain.book.dto.search.BookSearchDto;
import com.bookstore.readme.domain.book.dto.search.BookSearchReviewDto;
import com.bookstore.readme.domain.book.response.BookResponse;
import com.bookstore.readme.domain.book.service.BookSaveService;
import com.bookstore.readme.domain.book.service.BookSearchService;
import com.bookstore.readme.domain.book.service.ViewService;
import com.bookstore.readme.domain.book.service.page.SingleSortAndCategoryPageService;
import com.bookstore.readme.domain.book.service.page.SingleSortPageService;
import com.bookstore.readme.domain.category.service.CategorySearchService;
import com.bookstore.readme.domain.member.model.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "도서 API")
public class BookController {
    private final BookSearchService bookSearchService;
    private final ViewService viewService;


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


    @PutMapping("/book/{bookId}/view")
    @Operation(summary = "조회수 증가", description = "조회 수를 증가하기 위한 API")
    public ResponseEntity<BookResponse> bookView(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Parameter(description = "조회된 도서 아이디", required = true) @PathVariable(name = "bookId") Integer bookId
    ) {
        return ResponseEntity.ok(BookResponse.ok(viewService.addViewCount(memberDetails.getMemberId(), bookId.longValue())));
    }
}
