package com.bookstore.readme.domain.member.repository;

import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.social.domain.SocialId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndPassword(String email, String password);

    Optional<Member> findBySocialIdAndEmail(SocialId socialId, String email);

    Optional<Member> findByRefreshToken(String refreshToken);

    boolean existsByEmail(String email);

}
