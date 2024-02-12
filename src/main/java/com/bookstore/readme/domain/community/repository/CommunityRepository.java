package com.bookstore.readme.domain.community.repository;

import com.bookstore.readme.domain.community.domain.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Page<Community> findAllByMemberId(Long memberId, Pageable pageable);
}
