package com.bookstore.readme.domain.book.controller;

import com.bookstore.readme.domain.book.request.BookRequest;
import com.bookstore.readme.domain.book.response.BookResponse;
import com.bookstore.readme.domain.book.service.BookService;
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
    public ResponseEntity<BookResponse> bookList() {
        CacheControl cacheControl = CacheControl.maxAge(Duration.ofSeconds(30));
        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(bookService.bookList());
    }

    @GetMapping("/list/scroll")
    public ResponseEntity<BookResponse> bookScrollList(
            @RequestParam(defaultValue = "1", required = false) Integer bookId,
            @RequestParam(defaultValue = "10", required = false) Integer limit
    ) {
        CacheControl cacheControl = CacheControl.maxAge(Duration.ofSeconds(30));
        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(bookService.bookList(bookId, limit));
    }


    @PostMapping("/save")
    public ResponseEntity<BookResponse> bookSave(@RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.bookSave(request));
    }
}
