package com.bookstore.readme.domain.bookmark.controller;

import com.bookstore.readme.domain.bookmark.dto.BookmarkAndBookDto;
import com.bookstore.readme.domain.bookmark.dto.BookmarkCountDto;
import com.bookstore.readme.domain.bookmark.dto.BookmarkDto;
import com.bookstore.readme.domain.bookmark.response.BookmarkResponse;
import com.bookstore.readme.domain.bookmark.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "찜하기 API")
public class BookmarkController {

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

    @GetMapping("/bookmark/{id}/book")
    @Operation(summary = "상품 찜개수 조회", description = "상품이 찜된 개수 조회 API")
    public ResponseEntity<BookmarkResponse> searchBookmarkCountByBook(
            @Parameter(description = "찜 개수를 조회할 도서 아이디", required = true) @PathVariable(name = "id") Integer bookId
    ) {
        BookmarkCountDto bookmarkCountDto = bookmarkService.searchBookmarkCountByBook(bookId.longValue());
        return ResponseEntity.ok(BookmarkResponse.ok(bookmarkCountDto));
    }

    @GetMapping("/bookmark/{id}/member")
    @Operation(summary = "회원 찜개수 조회", description = "회원이 찜한 개수 조회 API")
    public ResponseEntity<BookmarkResponse> searchBookmarkCountByMember(
            @Parameter(description = "찜 개수를 조회할 도서 아이디", required = true) @PathVariable(name = "id") Integer memberId
    ) {
        BookmarkCountDto bookmarkCountDto = bookmarkService.searchBookmarkCountByMember(memberId.longValue());
        return ResponseEntity.ok(BookmarkResponse.ok(bookmarkCountDto));
    }

    @GetMapping("/bookmark/{id}/member/detail")
    @Operation(summary = "회원 찜개수 조회", description = "회원이 찜한 개수 조회 API")
    public ResponseEntity<BookmarkResponse> searchBookmarkAndBookByMember(
            @Parameter(description = "찜 개수를 조회할 도서 아이디", required = true) @PathVariable(name = "id") Integer memberId
    ) {
        BookmarkAndBookDto bookmarkAndBookDto = bookmarkService.searchBookmarkAndBookByMember(memberId.longValue());
        return ResponseEntity.ok(BookmarkResponse.ok(bookmarkAndBookDto));
    }
}
