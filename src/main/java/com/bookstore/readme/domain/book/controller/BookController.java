package com.bookstore.readme.domain.book.controller;

import com.bookstore.readme.domain.book.dto.SortType;
import com.bookstore.readme.domain.book.request.BookPageRequest;
import com.bookstore.readme.domain.book.request.BookRequest;
import com.bookstore.readme.domain.book.response.BookResponse;
import com.bookstore.readme.domain.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @GetMapping("/list")
    @Operation(summary = "도서 전체 조회", description = "도서를 전체 조회 합니다.", tags = {"도서 관련 Controller"})
    public ResponseEntity<BookResponse> bookList() {
        CacheControl cacheControl = CacheControl.maxAge(Duration.ofSeconds(30));
        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(bookService.bookList());
    }

    @GetMapping("/list/scroll")
    @Operation(summary = "도서 전체 스크롤 기능", description = "무한 스크롤을 위해 커서아이디와 여러 정렬 값이 필요합니다(필수는 아님).", tags = {"도서 관련 Controller"})
    public ResponseEntity<BookResponse> bookScrollList(@ModelAttribute BookPageRequest request) {
        CacheControl cacheControl = CacheControl.maxAge(Duration.ofSeconds(30));
        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(bookService.bookList(request));
    }


    @PostMapping("/save")
    @Operation(summary = "도서 저장 기능", description = "도서를 저장합니다.", tags = {"도서 관련 Controller"})
    public ResponseEntity<BookResponse> bookSave(@RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.bookSave(request));
    }
}
