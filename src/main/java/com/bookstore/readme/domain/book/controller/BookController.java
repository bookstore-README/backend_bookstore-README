package com.bookstore.readme.domain.book.controller;

import com.bookstore.readme.domain.book.dto.SortType;
import com.bookstore.readme.domain.book.request.BookPageRequest;
import com.bookstore.readme.domain.book.request.BookRequest;
import com.bookstore.readme.domain.book.response.BookResponse;
import com.bookstore.readme.domain.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
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

    @GetMapping("/list")
    @Operation(summary = "도서 전체 조회", description = "도서를 전체 조회하기 위한 API")
    public ResponseEntity<BookResponse> bookList() {
        CacheControl cacheControl = CacheControl.maxAge(Duration.ofSeconds(30));
        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(bookService.bookList());
    }

    @GetMapping("/list/scroll")
    @Operation(summary = "도서 전체 조회(커서 기반)", description = "무한 스크롤 기능을 위한 API")
    public ResponseEntity<BookResponse> bookScrollList(
            @ParameterObject
            @ModelAttribute BookPageRequest request
    ) {
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
}
