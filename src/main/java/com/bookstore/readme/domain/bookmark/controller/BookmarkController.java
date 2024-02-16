package com.bookstore.readme.domain.bookmark.controller;

import com.bookstore.readme.domain.bookmark.dto.BookmarkDto;
import com.bookstore.readme.domain.bookmark.dto.count.BookmarkCountByBookIdDto;
import com.bookstore.readme.domain.bookmark.dto.count.BookmarkCountByMemberIdDto;
import com.bookstore.readme.domain.bookmark.dto.page.BookmarkPageDto;
import com.bookstore.readme.domain.bookmark.request.BookmarkDeleteDto;
import com.bookstore.readme.domain.bookmark.request.BookmarkPageRequest;
import com.bookstore.readme.domain.bookmark.response.BookmarkResponse;
import com.bookstore.readme.domain.bookmark.service.BookmarkCountService;
import com.bookstore.readme.domain.bookmark.service.BookmarkDeleteService;
import com.bookstore.readme.domain.bookmark.service.BookmarkSearchService;
import com.bookstore.readme.domain.bookmark.service.BookmarkService;
import com.bookstore.readme.domain.member.model.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "찜하기 API")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final BookmarkCountService bookmarkCountService;
    private final BookmarkDeleteService bookmarkDeleteService;
    private final BookmarkSearchService bookmarkSearchService;

    @PostMapping("/bookmark/{bookId}")
    @Operation(summary = "찜하기", description = "로그인 한 회원으로 도서를 찜하는 API")
    public ResponseEntity<BookmarkResponse> addBookmark(
            @Parameter(description = "찜하기를 저장할 도서 아이디", required = true) @PathVariable(name = "bookId") Integer bookId,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        BookmarkDto bookmarkDto = bookmarkService.addBookmark(bookId.longValue(), memberDetails.getMemberId());
        return ResponseEntity.ok(BookmarkResponse.ok(bookmarkDto));
    }

    @GetMapping("/bookmark")
    @Operation(summary = "회원의 찜목록 조회", description = "회원 아이디로 찜목록 조회 API")
    public ResponseEntity<BookmarkResponse> searchBookmarkAndBookByMember(
            @ParameterObject @Valid BookmarkPageRequest request,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        BookmarkPageDto bookmarks = bookmarkSearchService.searchBookmarkAndBookByMember(memberDetails.getMemberId(), request.getOffset(), request.getLimit(), request.getSort());
        return ResponseEntity.ok(BookmarkResponse.ok(bookmarks));
    }

    @DeleteMapping("/bookmark")
    @Operation(summary = "찜 아이디 리스트로 삭제", description = "찜 아이디 리스트로 삭제 하는 API")
    public ResponseEntity<BookmarkResponse> deleteBookmark(
            @ParameterObject @Valid BookmarkDeleteDto request
    ) {
        return ResponseEntity.ok(BookmarkResponse.ok(bookmarkDeleteService.deleteAllByBookmarkId(request.getBookmarkIds())));
    }

    @GetMapping("/bookmark/{bookId}/book")
    @Operation(summary = "상품 찜개수 조회", description = "상품이 찜된 개수 조회 API")
    public ResponseEntity<BookmarkResponse> searchBookmarkCountByBook(
            @Parameter(description = "찜 개수를 조회할 도서 아이디", required = true) @PathVariable(name = "bookId") Integer bookId
    ) {
        BookmarkCountByBookIdDto result = bookmarkCountService.bookmarkCountByBookId(bookId.longValue());
        return ResponseEntity.ok(BookmarkResponse.ok(result));
    }

    @GetMapping("/bookmark/{memberId}/member")
    @Operation(summary = "회원 찜개수 조회", description = "회원이 찜한 개수 조회 API")
    public ResponseEntity<BookmarkResponse> searchBookmarkCountByMember(
            @Parameter(description = "찜 개수를 조회할 도서 아이디", required = true) @PathVariable(name = "memberId") Integer memberId
    ) {
        BookmarkCountByMemberIdDto result = bookmarkCountService.bookmarkCountByMemberId(memberId.longValue());
        return ResponseEntity.ok(BookmarkResponse.ok(result));
    }

    @GetMapping("/bookmark/{bookId}/check")
    @Operation(summary = "찜 여부 조회", description = "회원이 찜한 개수 조회 API")
    public ResponseEntity<BookmarkResponse> searchBookmarkCountByMember(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Parameter(description = "찜 여부를 확인할 도서 아이디", required = true) @PathVariable(name = "bookId") Integer bookId
    ) {
        boolean result = bookmarkService.checkMemberBookmark(memberDetails.getMemberId(), bookId.longValue());
        return ResponseEntity.ok(BookmarkResponse.ok(result));
    }
}
