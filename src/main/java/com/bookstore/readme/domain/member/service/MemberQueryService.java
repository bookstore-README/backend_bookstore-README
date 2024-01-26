package com.bookstore.readme.domain.member.service;

import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.dto.MemberJoinDto;
import com.bookstore.readme.domain.member.dto.MemberLoginDto;
import com.bookstore.readme.domain.member.exception.DuplicationMemberException;
import com.bookstore.readme.domain.member.exception.MemberCode;
import com.bookstore.readme.domain.member.exception.MemberErrorResponse;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public boolean save(MemberJoinDto memberJoinDto) {

        boolean isExist = memberRepository.existsByEmail(memberJoinDto.getEmail());
        if (isExist) {
            MemberErrorResponse memberErrorResponse = new MemberErrorResponse(MemberCode.DUPLICATE_MEMBER);
            throw new DuplicationMemberException(memberErrorResponse);
        }
        
        // 비밀번호 암호화
        memberJoinDto.setPassword(encoder.encode(memberJoinDto.getPassword()));

        Member entity = memberJoinDto.toEntity();
        memberRepository.save(entity);

        log.debug("Save Member Entity: {}", entity);
        return true;
    }
}
