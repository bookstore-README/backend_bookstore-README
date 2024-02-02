package com.bookstore.readme.domain.member.controller;

import com.bookstore.readme.domain.member.service.MemberService;
import com.bookstore.readme.domain.member.dto.MemberJoinDto;
import com.bookstore.readme.domain.member.dto.MemberLoginDto;
import com.bookstore.readme.domain.member.response.MemberResponse;
import com.bookstore.readme.domain.member.exception.DuplicationMemberException;
import com.bookstore.readme.domain.member.exception.MemberErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "회원 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    @Operation(summary = "회원 가입", description = "회원을 등록하기 위한 API")
    public ResponseEntity<MemberResponse> save(@RequestBody MemberJoinDto memberJoinDto) {
        return ResponseEntity.ok(memberService.memberJoin(memberJoinDto));
    }

//    @PostMapping("/sign-in")
//    public ResponseEntity<MemberResponse> login(@RequestBody MemberLoginDto memberLoginDto) {
//        System.out.println("MemberController: sign-in");
//        return ResponseEntity.ok(memberService.memberLogin(memberLoginDto));
//    }

    @ExceptionHandler(DuplicationMemberException.class)
    public ResponseEntity<MemberResponse> handler(DuplicationMemberException e) {

        MemberErrorResponse errorResponse = e.getMemberErrorResponse();
        MemberResponse response = new MemberResponse(
                errorResponse.getStatus(),
                errorResponse.getMessage(),
                null
        );

        return ResponseEntity.badRequest().body(response);
    }
}
