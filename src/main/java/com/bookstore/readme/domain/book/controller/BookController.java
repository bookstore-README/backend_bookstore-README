package com.bookstore.readme.domain.book.controller;

import com.bookstore.readme.domain.book.dto.BookSearchDto;
import com.bookstore.readme.domain.book.dto.SortType;
import com.bookstore.readme.domain.book.request.BookPageRequest;
import com.bookstore.readme.domain.book.request.BookRequest;
import com.bookstore.readme.domain.book.response.BookResponse;
import com.bookstore.readme.domain.book.service.BookSearchService;
import com.bookstore.readme.domain.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.repository.query.Param;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@Tag(name = "도서 API")
public class BookController {
    private final BookService bookService;
    private final BookSearchService bookSearchService;

    @GetMapping("/search/{bookId}")
    @Operation(summary = "도서 단일 조회", description = "도서 아이디로 단일 조회하는 API")
    public ResponseEntity<BookResponse> bookSearch(
            @Parameter(description = "도서를 조회할 아이디", required = true)
            @PathVariable(name = "bookId") Integer bookId
    ) {
        BookSearchDto bookSearchDto = bookSearchService.searchBook(bookId.longValue());
        return ResponseEntity.ok(BookResponse.ok(bookSearchDto));
    }

    @GetMapping("/list/scroll")
    @Operation(summary = "도서 전체 조회(커서 기반)", description = "무한 스크롤 기능을 위한 API")
    public ResponseEntity<BookResponse> bookScrollList(@ParameterObject @Valid BookPageRequest request) {
        CacheControl cacheControl = CacheControl.maxAge(Duration.ofSeconds(30));
        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(bookService.bookList(request));
    }

    @PostMapping("/save")
    @Operation(summary = "도서 저장", description = "도서를 저장하기 위한 API")
    public ResponseEntity<BookResponse> bookSave(@RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.bookSave(request));
    }

    @GetMapping("/{bookId}/bookmark")
    @Operation(summary = "도서 북마크 개수 조회", description = "도서의 북마크 개수를 가져오기 위한 API")
    public ResponseEntity<BookResponse> bookmarkCount(@Parameter(description = "북마크 개수를 가져올 도서 아이디") @PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.bookmarkCount(bookId));
    }
}
