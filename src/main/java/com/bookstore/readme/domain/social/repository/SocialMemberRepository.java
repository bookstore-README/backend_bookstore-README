package com.bookstore.readme.domain.social.repository;

import com.bookstore.readme.domain.social.domain.SocialId;
import com.bookstore.readme.domain.social.domain.SocialMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialMemberRepository extends JpaRepository <SocialMember, Long> {

    Optional<SocialMember> findBySocialId(SocialId socialId);

}
