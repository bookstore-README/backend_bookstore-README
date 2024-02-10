package com.bookstore.readme.domain.member.service;

import com.bookstore.readme.domain.file.service.FileService;
import com.bookstore.readme.domain.member.dto.*;
import com.bookstore.readme.domain.member.exception.DuplicationMemberEmailException;
import com.bookstore.readme.domain.member.exception.DuplicationNicknameException;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import com.bookstore.readme.domain.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final FileService fileService;
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
    public boolean changePassword(MemberPasswordUpdateDto memberPasswordUpdateDto) {
        Member member = memberRepository.findById(memberPasswordUpdateDto.getMemberId())
                .orElseThrow(() -> new NotFoundMemberByIdException(memberPasswordUpdateDto.getMemberId()));

        member.updateNewPassword(memberPasswordUpdateDto.getNewPassword(), encoder);
        memberRepository.saveAndFlush(member);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean changeProfile(MultipartFile profileImage, MemberUpdateDto memberUpdateDto) {
        Member member = memberRepository.findById(memberUpdateDto.getMemberId())
                .orElseThrow(() -> new NotFoundMemberByIdException(memberUpdateDto.getMemberId()));

        String fileUrl = null;

        // 파일 업로드
        if(!profileImage.isEmpty())
            fileUrl = fileService.saveProfileImage(profileImage);
        
        // 닉네임 수정 시 중복 확인
        if(!member.getNickname().equals(memberUpdateDto.getNickname())) {
            if(!memberRepository.existsByNickname(memberUpdateDto.getNickname())) {
                memberRepository.saveAndFlush(memberUpdateDto.toUpdateEntity(member, fileUrl));
            } else {
                throw new DuplicationNicknameException(memberUpdateDto.getNickname());
            }
        } else {
            memberRepository.saveAndFlush(memberUpdateDto.toUpdateEntity(member, fileUrl));
        }

        return true;
    }

    @Transactional
    public List<MemberReviewsDto> myReviews(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));

        List<Review> reviews = member.getReviews();

        return MemberReviewsDto.ofs(reviews);
    }
}
