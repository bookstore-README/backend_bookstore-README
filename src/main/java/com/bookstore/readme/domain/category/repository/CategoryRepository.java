package com.bookstore.readme.domain.category.repository;

import com.bookstore.readme.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
