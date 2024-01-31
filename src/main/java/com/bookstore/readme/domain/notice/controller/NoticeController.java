package com.bookstore.readme.domain.notice.controller;

import com.bookstore.readme.domain.notice.request.NoticeSaveRequest;
import com.bookstore.readme.domain.notice.response.NoticeResponse;
import com.bookstore.readme.domain.notice.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
@Tag(name = "커뮤니티 API")
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/search/{noticeId}")
    @Operation(summary = "게시글 단일 조회", description = "게시글 아이디로 단일 조회하는 API")
    public ResponseEntity<NoticeResponse> noticeSearch(@Parameter @PathVariable Integer noticeId) {
        NoticeResponse noticeResponse = noticeService.getNotice(noticeId.longValue());
        return ResponseEntity.ok(noticeResponse);
    }

    @GetMapping("/list")
    @Operation(summary = "게시글 전체 조회", description = "모든 회원이 작성한 게시글 전체를 조회하는 API")
    public ResponseEntity<NoticeResponse> noticeList() {
        NoticeResponse noticeResponse = noticeService.getNotices();
        return ResponseEntity.ok(noticeResponse);
    }

    @PostMapping("/save")
    @Operation(summary = "게시글 등록", description = "게시글을 등록하는 API")
    public ResponseEntity<NoticeResponse> noticeSave(@Valid @RequestBody NoticeSaveRequest request) {
        Long noticeId = noticeService.save(request);
        return ResponseEntity.ok(NoticeResponse.ok(noticeId));
    }

    @PostMapping("/delete/{noticeId}")
    @Operation(summary = "게시글 삭제", description = "게시글 아이디로 삭제하는 API")
    public ResponseEntity<NoticeResponse> noticeDelete(@Parameter @PathVariable Integer noticeId) {
        Long delete = noticeService.delete(noticeId.longValue());
        return ResponseEntity.ok(NoticeResponse.ok(delete));
    }
}
