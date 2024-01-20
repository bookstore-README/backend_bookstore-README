package com.bookstore.readme.domain.member.api;

import com.bookstore.readme.domain.member.application.MemberService;
import com.bookstore.readme.domain.member.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberApi {

    @Autowired
    private MemberService memberService;

    @PostMapping("/sign-up")
    public void save(MemberDto memberDto) {

        memberService.save(memberDto);

    }

}
