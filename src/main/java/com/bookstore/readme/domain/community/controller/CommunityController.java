package com.bookstore.readme.domain.community.controller;

import com.bookstore.readme.domain.community.dto.CommunityPageDto;
import com.bookstore.readme.domain.community.dto.CommunitySaveDto;
import com.bookstore.readme.domain.community.request.CommunityPageRequest;
import com.bookstore.readme.domain.community.request.CommunitySaveRequest;
import com.bookstore.readme.domain.community.response.CommunityResponse;
import com.bookstore.readme.domain.community.service.CommunityDeleteService;
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
    private final CommunityDeleteService communityDeleteService;

    @Operation(description = "커뮤니티 글 등록 API")
    @PostMapping
    public ResponseEntity<CommunityResponse> saveCommunity(@RequestBody @Valid CommunitySaveRequest request) {
        CommunitySaveDto result = communitySaveService.save(request);
        return ResponseEntity.ok(CommunityResponse.ok(result));
    }

    @Operation(description = "커뮤니티 글 페이징 전체 조회 API")
    @GetMapping
    public ResponseEntity<CommunityResponse> searchCommunityPage(
            @ParameterObject CommunityPageRequest request) {

        CommunityPageDto result = communitySearchService.searchCommunityPage(request.getCursorId(), request.getLimit());
        if (result.getCards().isEmpty())
            return ResponseEntity.ok(CommunityResponse.empty(result));
        else
            return ResponseEntity.ok(CommunityResponse.ok(result));
    }

    @Operation(description = "커뮤니티 글 페이징 회원 아이디 전체 조회 API")
    @GetMapping("/{memberId}")
    public ResponseEntity<CommunityResponse> searchCommunityPageByMemberId(
            @ParameterObject CommunityPageRequest request,
            @Parameter(name = "커뮤니티 글을 조회할 회원 아이디", required = true) @PathVariable(name = "memberId") Integer memberId) {

        CommunityPageDto result = communitySearchService.searchCommunityPage(request.getCursorId(), request.getLimit(), memberId);
        if (result.getCards().isEmpty())
            return ResponseEntity.ok(CommunityResponse.empty(result));
        else
            return ResponseEntity.ok(CommunityResponse.ok(result));
    }

    @Operation(description = "커뮤니티 아이디로 글 삭제 API")
    @DeleteMapping("/{communityId}")
    public ResponseEntity<CommunityResponse> deleteByCommunityId(
            @Parameter(name = "삭제할 커뮤니티 아이디", required = true) @PathVariable(name = "communityId") Integer communityId) {
        Long result = communityDeleteService.deleteByCommunityId(communityId.longValue());
        return ResponseEntity.ok(CommunityResponse.ok(result));
    }
}
