package com.bookstore.readme.domain.member.application;

import com.bookstore.readme.domain.member.domain.Member;
import com.bookstore.readme.domain.member.dto.MemberJoinDto;
import com.bookstore.readme.domain.member.dto.MemberLoginDto;
import com.bookstore.readme.domain.member.exception.DuplicationMemberException;
import com.bookstore.readme.domain.member.exception.MemberCode;
import com.bookstore.readme.domain.member.exception.MemberErrorResponse;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberQueryService {
    private final MemberRepository memberRepository;

    @Transactional
    public boolean save(MemberJoinDto memberJoinDto) {

        boolean isExist = memberRepository.existsByEmail(memberJoinDto.getEmail());
        if (isExist) {
            MemberErrorResponse memberErrorResponse = new MemberErrorResponse(MemberCode.DUPLICATE_MEMBER);
            throw new DuplicationMemberException(memberErrorResponse);
        }

        Member entity = memberJoinDto.toEntity();
        memberRepository.save(entity);

        log.debug("Save Member Entity: {}", entity);
        return true;
    }

    public Member login(MemberLoginDto memberLoginDto) {
        Member member = memberRepository.findByEmailAndPassword(memberLoginDto.getEmail(), memberLoginDto.getPassword()).orElse(null);
        System.out.println("member = " + member);
        return member;
    }
}
