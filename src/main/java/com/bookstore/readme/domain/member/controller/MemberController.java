package com.bookstore.readme.domain.member.controller;

import com.bookstore.readme.domain.member.dto.*;
import com.bookstore.readme.domain.member.service.MemberService;
import com.bookstore.readme.domain.member.response.MemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = "회원 로그인 API")
    public void login(@RequestBody MemberLoginDto memberLoginDto) {
        System.out.println("MemberController: sign-in");
    }

    @PostMapping("/member")
    @Operation(summary = "회원 가입", description = "회원을 등록하기 위한 API")
    public ResponseEntity<MemberResponse> save(@Valid @RequestBody MemberSaveDto memberSaveDto) {
        Long memberId = memberService.joinMember(memberSaveDto);
        return ResponseEntity.ok(MemberResponse.ok(memberId));
    }

    @GetMapping("/member/{memberId}")
    @Operation(summary = "회원 단일 조회", description = "회원을 단일 조회 하기위한 API")
    public ResponseEntity<MemberResponse> save(
            @Parameter(description = "조회할 회원 아이디", example = "1", required = true)
            @PathVariable(name = "memberId") Integer memberId) {
        MemberDto memberDto = memberService.searchMember(memberId.longValue());
        return ResponseEntity.ok(MemberResponse.ok(memberDto));
    }

    @PutMapping("/member/password")
    @Operation(summary = "비밀번호 변경", description = "마이페이지 비밀번호 변경 API")
    public ResponseEntity<MemberResponse> changePassword(@Valid @RequestBody MemberPasswordUpdateDto memberPasswordUpdateDto) {
        return ResponseEntity.ok(MemberResponse.ok(memberService.changePassword(memberPasswordUpdateDto)));
    }


    @PutMapping(value = "/member/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "프로필 수정", description = "마이페이지 프로필 수정 API")
    public ResponseEntity<MemberResponse> changeProfileImage(
            @RequestPart MemberUpdateDto memberUpdateDto,
            @Parameter(description = "업로드 할 프로필 이미지")
            @RequestPart(required = false) MultipartFile profileImage) {
        return ResponseEntity.ok(MemberResponse.ok(memberService.changeProfile(profileImage, memberUpdateDto)));
    }
}
