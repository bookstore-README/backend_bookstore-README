package com.bookstore.readme.domain.community.controller;

import com.bookstore.readme.domain.community.dto.CommunitySaveDto;
import com.bookstore.readme.domain.community.request.CommunityPageRequest;
import com.bookstore.readme.domain.community.request.CommunitySaveRequest;
import com.bookstore.readme.domain.community.response.CommunityResponse;
import com.bookstore.readme.domain.community.service.CommunitySaveService;
import com.bookstore.readme.domain.community.service.CommunitySearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
@Tag(name = "커뮤니티 API")
public class CommunityController {

    private final CommunitySaveService communitySaveService;
    private final CommunitySearchService communitySearchService;

    @Operation(description = "커뮤니티 글 등록 API")
    @PostMapping
    public ResponseEntity<CommunityResponse> saveCommunity(@RequestBody @Valid CommunitySaveRequest request) {
        CommunitySaveDto result = communitySaveService.save(request);
        return ResponseEntity.ok(CommunityResponse.ok(result));
    }

    @Operation(description = "커뮤니티 글 등록 API")
    @GetMapping
    public ResponseEntity<CommunityResponse> searchCommunityPage(
            @ParameterObject CommunityPageRequest request) {
        return ResponseEntity.ok(CommunityResponse.ok(communitySearchService.searchCommunityPage(
                request.getCursorId(),
                request.getLimit()
        )));
    }
}
