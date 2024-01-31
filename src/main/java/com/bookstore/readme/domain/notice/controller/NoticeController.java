package com.bookstore.readme.domain.notice.controller;

import com.bookstore.readme.domain.notice.request.NoticeSaveRequest;
import com.bookstore.readme.domain.notice.response.NoticeResponse;
import com.bookstore.readme.domain.notice.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping("/save")
    public ResponseEntity<NoticeResponse> noticeSave(@ParameterObject @Valid @RequestBody NoticeSaveRequest request) {
        Long noticeId = noticeService.save(request);
        return ResponseEntity.ok(NoticeResponse.ok(noticeId));
    }
}