package com.bookstore.readme.domain.bookmark.controller;

import com.bookstore.readme.domain.bookmark.dto.BookmarkDto;
import com.bookstore.readme.domain.bookmark.response.BookmarkResponse;
import com.bookstore.readme.domain.bookmark.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "찜하기 API")
public class BookMarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/bookmark/{bookId}/{memberId}")
    @Operation(summary = "찜하기", description = "회원 아이디와 도서 아이디로 찜하는 API")
    public ResponseEntity<BookmarkResponse> addBookmark(
            @Parameter(description = "찜하기를 저장할 도서 아이디", required = true) @PathVariable(name = "bookId") Integer bookId,
            @Parameter(description = "찜하기를 저장할 회원 아이디", required = true) @PathVariable(name = "memberId") Integer memberId
    ) {
        BookmarkDto bookmarkDto = bookmarkService.addBookmark(bookId.longValue(), memberId.longValue());
        return ResponseEntity.ok(BookmarkResponse.ok(bookmarkDto));
    }

}
