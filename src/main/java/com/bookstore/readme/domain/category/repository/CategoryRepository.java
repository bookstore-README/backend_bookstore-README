package com.bookstore.readme.domain.category.repository;

import com.bookstore.readme.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByMainIdAndSubId(Long mainId, Long subId);

    List<Category> findAllByIdIn(Collection<Long> id);


}
