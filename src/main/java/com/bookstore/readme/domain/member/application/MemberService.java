package com.bookstore.readme.domain.member.application;

import com.bookstore.readme.domain.member.domain.Member;
import com.bookstore.readme.domain.member.dto.MemberDto;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member save(MemberDto memberDto) {

        Member member = memberDto.toEntity();

        Member save = memberRepository.save(member);
        System.out.println("save = " + save);

        return save;
    }
}
