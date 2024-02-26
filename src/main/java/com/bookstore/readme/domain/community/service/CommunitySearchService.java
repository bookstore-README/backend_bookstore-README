package com.bookstore.readme.domain.community.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.community.domain.Community;
import com.bookstore.readme.domain.community.domain.Emoji;
import com.bookstore.readme.domain.community.domain.MemberEmoji;
import com.bookstore.readme.domain.community.dto.*;
import com.bookstore.readme.domain.community.repository.CommunityRepository;
import com.bookstore.readme.domain.community.repository.MemberEmojiRepository;
import com.bookstore.readme.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunitySearchService {
    private final CommunityRepository communityRepository;
    private final MemberEmojiRepository memberEmojiRepository;

    @Transactional
    public CommunityPageDto searchCommunityPage(Long memberId, Integer cursorId, Integer limit) {
        PageRequest pageRequest = PageRequest.of(cursorId, limit, Sort.by(Sort.Order.desc("createDate")));
        Page<Community> pages = communityRepository.findAll(pageRequest);
        List<Community> content = pages.getContent();
        List<CommunityPageInfoDto> pageInfos = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            Community community = content.get(i);
            Book book = community.getBook();
            Member member = community.getMember();

            CommunityBookDto communityBookDto = CommunityBookDto.of(book);
            CommunityMemberDto communityMemberDto = CommunityMemberDto.of(member);

            Emoji emoji = community.getEmoji();
            Long id = emoji.getId();
            MemberEmoji memberEmoji = memberEmojiRepository.findByMemberIdAndCommunityId(memberId, community.getId()).orElseGet(() -> MemberEmoji.builder()
                    .isHeart(false)
                    .isSmile(false)
                    .isExcited(false)
                    .isAngry(false)
                    .isCrying(false)
                    .build());

            List<EmojiInfoDto> emojis = List.of(
                    new EmojiInfoDto("heart", emoji.getHeart(), memberEmoji.getIsHeart()),
                    new EmojiInfoDto("smile", emoji.getSmile(), memberEmoji.getIsSmile()),
                    new EmojiInfoDto("excited", emoji.getExcited(), memberEmoji.getIsExcited()),
                    new EmojiInfoDto("angry", emoji.getAngry(), memberEmoji.getIsAngry()),
                    new EmojiInfoDto("crying", emoji.getCrying(), memberEmoji.getIsCrying())
            );

            CommunityPageInfoDto pageInfo = CommunityPageInfoDto.builder()
                    .communityId(community.getId())
                    .content(community.getContent())
                    .writer(communityMemberDto)
                    .bookInfo(communityBookDto)
                    .emojiInfo(new EmojiDto(id, emojis))
                    .createDate(community.getCreateDate())
                    .updateDate(community.getUpdateDate())
                    .build();

            pageInfos.add(pageInfo);
        }

        return CommunityPageDto.builder()
                .total(content.size())
                .limit(limit)
                .cursorId(pages.hasNext() ? cursorId + 1 : -1)
                .cards(pageInfos)
                .build();
    }

    @Transactional
    public CommunityPageDto searchCommunityPage(Integer cursorId, Integer limit, Integer memberId) {
        PageRequest pageRequest = PageRequest.of(cursorId, limit, Sort.by(Sort.Order.desc("createDate")));
        Page<Community> pages = communityRepository.findAllByMemberId(memberId.longValue(), pageRequest);
        List<Community> content = pages.getContent();
        List<CommunityPageInfoDto> pageInfos = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            Community community = content.get(i);
            Book book = community.getBook();
            Member member = community.getMember();

            CommunityBookDto communityBookDto = CommunityBookDto.of(book);
            CommunityMemberDto communityMemberDto = CommunityMemberDto.of(member);

            Emoji emoji = community.getEmoji();
            Long id = emoji.getId();
            MemberEmoji memberEmoji = memberEmojiRepository.findByMemberIdAndCommunityId(memberId.longValue(), community.getId()).orElseGet(() -> MemberEmoji.builder()
                    .isHeart(false)
                    .isSmile(false)
                    .isExcited(false)
                    .isAngry(false)
                    .isCrying(false)
                    .build());

            List<EmojiInfoDto> emojis = List.of(
                    new EmojiInfoDto("heart", emoji.getHeart(), memberEmoji.getIsHeart()),
                    new EmojiInfoDto("smile", emoji.getSmile(), memberEmoji.getIsSmile()),
                    new EmojiInfoDto("excited", emoji.getExcited(), memberEmoji.getIsExcited()),
                    new EmojiInfoDto("angry", emoji.getAngry(), memberEmoji.getIsAngry()),
                    new EmojiInfoDto("crying", emoji.getCrying(), memberEmoji.getIsCrying())
            );


            CommunityPageInfoDto pageInfo = CommunityPageInfoDto.builder()
                    .communityId(community.getId())
                    .content(community.getContent())
                    .writer(communityMemberDto)
                    .bookInfo(communityBookDto)
                    .emojiInfo(new EmojiDto(id, emojis))
                    .createDate(community.getCreateDate())
                    .updateDate(community.getUpdateDate())
                    .build();

            pageInfos.add(pageInfo);
        }

        return CommunityPageDto.builder()
                .total(content.size())
                .limit(limit)
                .cursorId(pages.hasNext() ? cursorId + 1 : -1)
                .cards(pageInfos)
                .build();
    }
}
