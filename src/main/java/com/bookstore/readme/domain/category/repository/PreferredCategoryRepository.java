package com.bookstore.readme.domain.category.repository;

import com.bookstore.readme.domain.category.domain.PreferredCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreferredCategoryRepository extends JpaRepository<PreferredCategory, Long> {

    long deleteByMemberId(Long memberId);

}
