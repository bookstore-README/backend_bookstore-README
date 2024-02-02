package com.bookstore.readme.domain.bookmark.controller;

import com.bookstore.readme.domain.bookmark.dto.BookmarkDto;
import com.bookstore.readme.domain.bookmark.response.BookmarkResponse;
import com.bookstore.readme.domain.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookMarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/bookmark/{bookId}/{memberId}")
    public ResponseEntity<BookmarkResponse> addBookmark(
            @PathVariable(name = "bookId") Integer bookId,
            @PathVariable(name = "memberId") Integer memberId
    ) {
        BookmarkDto bookmarkDto = bookmarkService.addBookmark(bookId.longValue(), memberId.longValue());
        return ResponseEntity.ok(BookmarkResponse.ok(bookmarkDto));
    }

}
