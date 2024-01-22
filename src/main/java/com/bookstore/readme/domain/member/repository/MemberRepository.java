package com.bookstore.readme.domain.member.repository;

import com.bookstore.readme.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    public Optional<Member> findByEmail(Member member);

    public Optional<Member> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

}
