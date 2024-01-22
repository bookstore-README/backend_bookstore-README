package com.bookstore.readme.domain.member.application;

import com.bookstore.readme.domain.member.domain.Member;
import com.bookstore.readme.domain.member.dto.MemberJoinDto;
import com.bookstore.readme.domain.member.dto.MemberLoginDto;
import com.bookstore.readme.domain.member.dto.MemberResponse;
import com.bookstore.readme.domain.member.exception.DuplicationMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
@RequiredArgsConstructor
public class MemberService {
    
    private final MemberQueryService memberQueryService;

    public MemberResponse memberJoin(MemberJoinDto memberJoinDto) {
        boolean save = memberQueryService.save(memberJoinDto);
        return MemberResponse
                .builder()
                .status(200)
                .message("Success")
                .data(save)
                .build();
    }

    public MemberResponse memberLogin(MemberLoginDto memberLoginDto) {
        Member member = memberQueryService.login(memberLoginDto);
        return MemberResponse
                .builder()
                .status(200)
                .message("Login Success")
                .data(member)
                .build();
    }
}
