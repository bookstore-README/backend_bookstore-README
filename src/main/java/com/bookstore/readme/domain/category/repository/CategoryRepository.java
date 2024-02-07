package com.bookstore.readme.domain.category.repository;

import com.bookstore.readme.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByMainIdAndSubId(Long mainId, Long subId);
}
