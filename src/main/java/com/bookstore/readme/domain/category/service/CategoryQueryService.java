package com.bookstore.readme.domain.category.service;

import com.bookstore.readme.domain.category.domain.Category;
import com.bookstore.readme.domain.category.exception.DuplicationCategoryNameException;
import com.bookstore.readme.domain.category.exception.NotFoundCategoryByIdException;
import com.bookstore.readme.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryQueryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public List<Category> searchAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Long save(Category category) {
        //카테고리 이름 중복 검사
        boolean existName = categoryRepository.existsByCategoryName(category.getCategoryName());
        if (existName)
            throw new DuplicationCategoryNameException(category.getCategoryName());

        Category save = categoryRepository.save(category);
        return save.getId();
    }

    @Transactional
    public Category update(Long categoryId, String newCategoryName) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundCategoryByIdException(categoryId));
        category.changeCategoryName(newCategoryName);
        return category;
    }

    @Transactional
    public Long delete(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return categoryId;
    }

}
