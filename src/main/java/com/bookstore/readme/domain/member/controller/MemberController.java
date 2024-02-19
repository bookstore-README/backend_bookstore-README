package com.bookstore.readme.domain.member.controller;

import com.bookstore.readme.domain.member.dto.*;
import com.bookstore.readme.domain.member.model.MemberDetails;
import com.bookstore.readme.domain.member.service.MemberService;
import com.bookstore.readme.domain.member.response.MemberResponse;
import com.bookstore.readme.domain.review.dto.ReviewSearchDto;
import com.bookstore.readme.domain.review.service.ReviewSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원 API")
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("")
    @Operation(summary = "회원 가입", description = "회원을 등록하기 위한 API")
    public ResponseEntity<MemberResponse> save(@Valid @RequestBody MemberSaveDto memberSaveDto) {
        Long memberId = memberService.joinMember(memberSaveDto);
        return ResponseEntity.ok(MemberResponse.ok(memberId));
    }

    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = "회원 로그인 API")
    public ResponseEntity<MemberResponse> login(@RequestBody MemberLoginDto memberLoginDto) {
        System.out.println("MemberController: sign-in");
        return ResponseEntity.ok(MemberResponse.ok(memberLoginDto.getEmail()));
    }

    @GetMapping("")
    @Operation(summary = "회원 본인 조회", description = "회원 본인 조회 API")
    public ResponseEntity<MemberResponse> searchMemberOne(
            @AuthenticationPrincipal MemberDetails memberDetails) {
        MemberDto memberDto = memberService.searchMember(memberDetails.getMemberId());
        return ResponseEntity.ok(MemberResponse.ok(memberDto));
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "회원 단일 조회", description = "회원을 단일 조회 하기위한 API")
    public ResponseEntity<MemberResponse> searchMemberOne(
            @Parameter(description = "조회할 회원 아이디", example = "1", required = true)
            @PathVariable(name = "memberId") Integer memberId) {
        MemberDto memberDto = memberService.searchMember(memberId.longValue());
        return ResponseEntity.ok(MemberResponse.ok(memberDto));
    }

    @PutMapping("/password")
    @Operation(summary = "비밀번호 변경", description = "마이페이지 비밀번호 변경 API")
    public ResponseEntity<MemberResponse> changePassword(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Valid @RequestBody MemberPasswordUpdateDto memberPasswordUpdateDto) {
        return ResponseEntity.ok(MemberResponse.ok(memberService.changePassword(memberDetails, memberPasswordUpdateDto)));
    }


    @PutMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "프로필 수정", description = "마이페이지 프로필 수정 API")
    public ResponseEntity<MemberResponse> changeProfileImage(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestPart MemberUpdateDto memberUpdateDto,
            @Parameter(description = "업로드 할 프로필 이미지")
            @RequestPart(required = false) MultipartFile profileImage) {
        return ResponseEntity.ok(MemberResponse.ok(memberService.changeProfile(memberDetails, profileImage, memberUpdateDto)));
    }

    @GetMapping("/reviews")
    @Operation(summary = "나의 리뷰 목록", description = "마이페이지 리뷰 목록 조회 API")
    public ResponseEntity<MemberResponse> myReviews(
            @AuthenticationPrincipal MemberDetails memberDetails) {

        List<MemberReviewsDto> memberReviewsDtos = memberService.myReviews(memberDetails.getMemberId());

        return ResponseEntity.ok(MemberResponse.ok(memberReviewsDtos));
    }

    @PutMapping("/categories")
    @Operation(summary = "선호 장르 선택", description = "마이페이지 설정 선호 장르 API")
    public ResponseEntity<MemberResponse> changeCategories(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody MemberCategoryDto memberCategoryDto
    ) {

        memberService.changeCategories(memberDetails, memberCategoryDto);

        return ResponseEntity.ok(MemberResponse.ok(null));
    }
}
