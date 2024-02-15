package com.bookstore.readme.domain.community.repository;

import com.bookstore.readme.domain.community.domain.MemberEmoji;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberEmojiRepository extends JpaRepository<MemberEmoji, Long> {

    Optional<MemberEmoji> findByMemberIdAndCommunityId(Long memberId, Long communityId);
}
