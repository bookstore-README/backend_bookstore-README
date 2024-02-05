package com.bookstore.readme.domain.member.controller;

import com.bookstore.readme.domain.member.dto.MemberDto;
import com.bookstore.readme.domain.member.dto.MemberPasswordUpdateDto;
import com.bookstore.readme.domain.member.dto.MemberUpdateDto;
import com.bookstore.readme.domain.member.service.MemberService;
import com.bookstore.readme.domain.member.dto.MemberSaveDto;
import com.bookstore.readme.domain.member.response.MemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원 API")
public class MemberController {

    private final MemberService memberService;

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

    @PutMapping
    @Operation(summary = "비밀번호 변경", description = "마이페이지 비밀번호 변경 API")
    public ResponseEntity<Boolean> changePassword(@Valid @RequestBody MemberPasswordUpdateDto memberPasswordUpdateDto) {
        return ResponseEntity.ok(memberService.changePassword(memberPasswordUpdateDto));
    }

    @PostMapping
    @Operation(summary = "프로필 이미지 업로드", description = "마이페이지 프로필 이미지 업로드 API")
    public ResponseEntity<MemberResponse> initProfileImage(@RequestBody MemberUpdateDto memberUpdateDto) {
        return ResponseEntity.ok(MemberResponse.ok(null));
    }

    @PutMapping
    @Operation(summary = "프로필 이미지 수정", description = "마이페이지 프로필 이미지 수정 API")
    public ResponseEntity<MemberResponse> changeProfileImage(@RequestBody MemberUpdateDto memberUpdateDto) {
        return ResponseEntity.ok(MemberResponse.ok(null));
    }

    @PutMapping
    @Operation(summary = "닉네임 수정", description = "마이페이지 닉네임 수정 API")
    public ResponseEntity<MemberResponse> changeNickname(@RequestBody MemberPasswordUpdateDto memberUpdateDto) {
        return ResponseEntity.ok(MemberResponse.ok(null));
    }

//    @PostMapping("/sign-in")
//    public ResponseEntity<MemberResponse> login(@RequestBody MemberLoginDto memberLoginDto) {
//        System.out.println("MemberController: sign-in");
//        return ResponseEntity.ok(memberService.memberLogin(memberLoginDto));
//    }
}
