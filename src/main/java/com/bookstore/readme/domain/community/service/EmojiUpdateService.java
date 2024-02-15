package com.bookstore.readme.domain.community.service;

import com.bookstore.readme.domain.community.domain.Community;
import com.bookstore.readme.domain.community.domain.Emoji;
import com.bookstore.readme.domain.community.domain.MemberEmoji;
import com.bookstore.readme.domain.community.dto.EmojiType;
import com.bookstore.readme.domain.community.dto.EmojiUpdateDto;
import com.bookstore.readme.domain.community.exception.NotFoundCommunityByIdException;
import com.bookstore.readme.domain.community.repository.CommunityRepository;
import com.bookstore.readme.domain.community.repository.EmojiRepository;
import com.bookstore.readme.domain.community.repository.MemberEmojiRepository;
import com.bookstore.readme.domain.community.request.EmojiRequest;
import com.bookstore.readme.domain.community.request.MemberEmojiRequest;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmojiUpdateService {
    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;
    private final MemberEmojiRepository emojiRepository;

    @Transactional
    public EmojiUpdateDto update(Long memberId, Long communityId, EmojiRequest request) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new NotFoundCommunityByIdException(communityId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));

        Emoji emoji = community.getEmoji();
        MemberEmoji memberEmoji = emojiRepository.findByMemberIdAndCommunityId(memberId, communityId)
                .orElseGet(() -> MemberEmoji.builder()
                        .isHeart(false)
                        .isSmile(false)
                        .isExcited(false)
                        .isAngry(false)
                        .isCrying(false)
                        .build());


        EmojiType emojiType = request.getType();
        if (memberEmoji.getCheck(emojiType.getEmojiType()) != request.getCheck()) {
            memberEmoji.changeEmoji(emojiType.getEmojiType(), request.getCheck());

            if (request.getCheck())
                emoji.add(emojiType.getEmojiType());
            else
                emoji.sub(emojiType.getEmojiType());
        }

        memberEmoji.changeMember(member);
        memberEmoji.changeCommunity(community);
        emojiRepository.save(memberEmoji);


        return EmojiUpdateDto.builder()
                .memberEmojiId(memberEmoji.getId())
                .memberId(memberId)
                .communityId(communityId)
                .build();
    }
}
