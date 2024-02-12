package com.bookstore.readme.domain.community.controller;

import com.bookstore.readme.domain.community.dto.CommunitySaveDto;
import com.bookstore.readme.domain.community.request.CommunitySaveRequest;
import com.bookstore.readme.domain.community.response.CommunityResponse;
import com.bookstore.readme.domain.community.service.CommunitySaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
@Tag(name = "커뮤니티 API")
public class CommunityController {

    private final CommunitySaveService communitySaveService;

    @Operation(description = "커뮤니티 글 등록 API")
    @PostMapping
    public ResponseEntity<CommunityResponse> saveCommunity(@RequestBody @Valid CommunitySaveRequest request) {
        CommunitySaveDto result = communitySaveService.save(request);
        return ResponseEntity.ok(CommunityResponse.ok(result));
    }
}
