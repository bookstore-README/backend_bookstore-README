package com.bookstore.readme.domain.member.api;

import com.bookstore.readme.domain.member.application.MemberService;
import com.bookstore.readme.domain.member.dto.MemberDto;
import com.bookstore.readme.domain.member.dto.MemberJoinDto;
import com.bookstore.readme.domain.member.dto.MemberLoginDto;
import com.bookstore.readme.domain.member.dto.MemberResponse;
import com.bookstore.readme.domain.member.exception.DuplicationMemberException;
import com.bookstore.readme.domain.member.exception.MemberErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApi {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<MemberResponse> save(@RequestBody MemberJoinDto memberJoinDto) {
        return ResponseEntity.ok(memberService.memberJoin(memberJoinDto));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<MemberResponse> login(@RequestBody MemberLoginDto memberLoginDto) {
        return ResponseEntity.ok(memberService.memberLogin(memberLoginDto));
    }

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
