package com.bookstore.readme.domain.category.service;

import com.bookstore.readme.domain.category.domain.Category;
import com.bookstore.readme.domain.category.dto.CategoryDto;
import com.bookstore.readme.domain.category.dto.CategoryInfo;
import com.bookstore.readme.domain.category.exception.NotFoundCategoryByIdException;
import com.bookstore.readme.domain.category.exception.NotFoundCategoryByMainSubIdException;
import com.bookstore.readme.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategorySearchService {
    private final CategoryRepository categoryRepository;

    public CategoryDto searchCategory() {
        List<Category> categories = categoryRepository.findAll();

        //국내도서
        List<CategoryInfo> domestic = categories.stream()
                .filter(category -> category.getMainId() == 0)
                .map(CategoryInfo::of)
                .toList();

        List<CategoryInfo> foreign = categories.stream()
                .filter(category -> category.getMainId() == 1)
                .map(CategoryInfo::of)
                .toList();

        return CategoryDto.builder()
                .domestic(domestic)
                .foreign(foreign)
                .build();
    }

    public CategoryInfo searchCategoryInfo(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId.longValue())
                .orElseThrow(() -> new NotFoundCategoryByIdException(categoryId.longValue()));

        return CategoryInfo.of(category);
    }

    public CategoryInfo searchCategoryInfo(Integer mainId, Integer subId) {
        Category category = categoryRepository.findByMainIdAndSubId(mainId.longValue(), subId.longValue())
                .orElseThrow(() -> new NotFoundCategoryByMainSubIdException(mainId.longValue(), subId.longValue()));

        return CategoryInfo.of(category);
    }

    public List<CategoryInfo> categoryInfos(List<Integer> categories) {
        List<Category> categoryList = categoryRepository.findAllByIdIn(categories);

        return categoryList.stream()
                .map(CategoryInfo::of)
                .toList();
    }
}
