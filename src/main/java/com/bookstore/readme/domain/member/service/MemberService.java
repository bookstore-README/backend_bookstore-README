package com.bookstore.readme.domain.member.service;

import com.bookstore.readme.domain.member.dto.MemberSaveDto;
import com.bookstore.readme.domain.member.exception.DuplicationMemberEmailException;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long joinMember(MemberSaveDto memberSaveDto) {
        Member member = memberSaveDto.toEntity();
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new DuplicationMemberEmailException(member.getEmail());
        }

        memberRepository.save(member);
        return member.getId();
    }

//    public MemberResponse memberLogin(MemberLoginDto memberLoginDto) {
//        Member member = memberQueryService.login(memberLoginDto);
//        return MemberResponse
//                .builder()
//                .status(200)
//                .message("Login Success")
//                .data(member)
//                .build();
//    }
}
