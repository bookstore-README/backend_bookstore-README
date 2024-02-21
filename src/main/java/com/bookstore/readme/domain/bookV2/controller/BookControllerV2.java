package com.bookstore.readme.domain.bookV2.controller;

import com.bookstore.readme.domain.bookV2.dto.Pagination;
import com.bookstore.readme.domain.bookV2.request.BookPageRequest;
import com.bookstore.readme.domain.bookV2.request.NavigationMethod;
import com.bookstore.readme.domain.bookV2.service.InfinityScrollService;
import com.bookstore.readme.domain.bookV2.service.PaginationService;
import com.bookstore.readme.domain.member.model.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book/v2")
@Tag(name = "도서 API")
public class BookControllerV2 {
    private final PaginationService paginationService;
    private final InfinityScrollService infinityScrollService;

    @GetMapping
    @Operation(summary = "도서 페이징 조회", description = "도서 페이징 조회 API")
    public ResponseEntity<Object> bookPage(
            @Parameter(description = "페이징 방법", required = true) NavigationMethod navigationMethod,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @ParameterObject @Valid BookPageRequest request) {
        if (navigationMethod == NavigationMethod.PAGINATION)
            return ResponseEntity.ok(paginationService.bookPage(memberDetails == null ? -1 : memberDetails.getMemberId(), request));
        else
            return ResponseEntity.ok(infinityScrollService.bookPage(memberDetails == null ? -1 : memberDetails.getMemberId(), request));
    }
}
