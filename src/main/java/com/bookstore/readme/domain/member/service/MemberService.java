package com.bookstore.readme.domain.member.service;

import com.bookstore.readme.domain.member.dto.MemberDto;
import com.bookstore.readme.domain.member.dto.MemberSaveDto;
import com.bookstore.readme.domain.member.dto.MemberPasswordUpdateDto;
import com.bookstore.readme.domain.member.exception.DuplicationMemberEmailException;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public Long joinMember(MemberSaveDto memberSaveDto) {
        Member member = memberSaveDto.toEntity();
        member.passwordEncode(encoder);
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new DuplicationMemberEmailException(member.getEmail());
        }

        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public MemberDto searchMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));

        return MemberDto.of(member);
    }

    @Transactional
    public boolean changePassword(MemberPasswordUpdateDto memberUpdateDto) {
        Member member = memberRepository.findById(memberUpdateDto.getMember_id())
                .orElseThrow(() -> new NotFoundMemberByIdException(memberUpdateDto.getMember_id()));

        member.updateNewPassword(memberUpdateDto.getNew_password(), encoder);
        memberRepository.saveAndFlush(member);
        return true;
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
