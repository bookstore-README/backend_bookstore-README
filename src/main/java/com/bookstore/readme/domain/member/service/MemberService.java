package com.bookstore.readme.domain.member.service;

import com.bookstore.readme.common.utils.UrlUtils;
import com.bookstore.readme.domain.category.domain.Category;
import com.bookstore.readme.domain.category.domain.PreferredCategory;
import com.bookstore.readme.domain.category.exception.NotFoundCategoryByIdException;
import com.bookstore.readme.domain.category.repository.CategoryRepository;
import com.bookstore.readme.domain.category.repository.PreferredCategoryRepository;
import com.bookstore.readme.domain.file.service.FileService;
import com.bookstore.readme.domain.member.dto.*;
import com.bookstore.readme.domain.member.exception.DuplicationMemberEmailException;
import com.bookstore.readme.domain.member.exception.DuplicationNicknameException;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.model.MemberDetails;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import com.bookstore.readme.domain.review.domain.Review;
import jakarta.servlet.http.HttpServletRequest;
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
    private final CategoryRepository categoryRepository;
    private final PreferredCategoryRepository preferredCategoryRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public Long joinMember(MemberSaveDto memberSaveDto) {
        // 존재하는 아이디 여부
        if (memberRepository.existsByEmail(memberSaveDto.getEmail())) {
            throw new DuplicationMemberEmailException(memberSaveDto.getEmail());
        }

        // 존재하는 닉네임 여부
        if(memberRepository.existsByNickname(memberSaveDto.getNickname())) {
            throw new DuplicationNicknameException(memberSaveDto.getNickname());
        }

        Member member = memberSaveDto.toEntity();
        member.passwordEncode(encoder);

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
    public boolean changePassword(
            MemberDetails memberDetails,
            MemberPasswordUpdateDto memberPasswordUpdateDto) {
        Member member = memberRepository.findById(memberDetails.getMemberId())
                .orElseThrow(() -> new NotFoundMemberByIdException(memberDetails.getMemberId()));

        member.updateNewPassword(memberPasswordUpdateDto.getNewPassword(), encoder);
        memberRepository.saveAndFlush(member);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean changeProfile(HttpServletRequest request, MemberDetails memberDetails
            , MultipartFile profileImage, MemberUpdateDto memberUpdateDto) {
        Member member = memberRepository.findById(memberDetails.getMemberId())
                .orElseThrow(() -> new NotFoundMemberByIdException(memberDetails.getMemberId()));

        String fileUrl = null;

        // 파일 업로드
        if(null != profileImage && !profileImage.isEmpty())
            fileUrl = UrlUtils.getBaseUrl(request) + fileService.saveProfileImage(profileImage);
        
        // 닉네임 수정 시 중복 확인
        if(!member.getNickname().equals(memberUpdateDto.getNickname())) {
            if(!memberRepository.existsByNickname(memberUpdateDto.getNickname())) {
                memberRepository.saveAndFlush(memberUpdateDto.toUpdateEntity(member
                        , null == fileUrl ? member.getProfileImage() : fileUrl));
            } else {
                throw new DuplicationNicknameException(memberUpdateDto.getNickname());
            }
        } else {
            memberRepository.saveAndFlush(memberUpdateDto.toUpdateEntity(member
                    , null == fileUrl ? member.getProfileImage() : fileUrl));
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

    @Transactional(rollbackFor = Exception.class)
    public void changeCategories(MemberDetails memberDetails, MemberCategoryDto memberCategoryDto) {
        Member member = memberRepository.findById(memberDetails.getMemberId())
                .orElseThrow(() -> new NotFoundMemberByIdException(memberDetails.getMemberId()));

        preferredCategoryRepository.deleteByMemberId(member.getId());

        for(Integer categoryId : memberCategoryDto.getCategories()) {
            Category category = categoryRepository.findById(categoryId.longValue())
                    .orElseThrow(() -> new NotFoundCategoryByIdException(categoryId.longValue()));

            PreferredCategory preferredCategory = PreferredCategory.builder()
                    .member(member)
                    .category(category)
                    .build();

            preferredCategoryRepository.save(preferredCategory);

            preferredCategory.changeMember(member);
        }
    }
}
