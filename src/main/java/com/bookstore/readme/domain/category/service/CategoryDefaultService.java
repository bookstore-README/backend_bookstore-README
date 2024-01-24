package com.bookstore.readme.domain.category.service;

import com.bookstore.readme.domain.category.domain.Category;
import com.bookstore.readme.domain.category.dto.CategoryDto;
import com.bookstore.readme.domain.category.request.CategoryRequest;
import com.bookstore.readme.domain.category.request.CategoryUpdateRequest;
import com.bookstore.readme.domain.category.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class CategoryDefaultService implements CategoryService {
    private final CategoryQueryService categoryQueryService;

    @Override
    public CategoryResponse categoryList() {
        List<CategoryDto> convertCategories = categoryQueryService.searchAll()
                .stream()
                .map(CategoryDto::toCategory)
                .toList();

        return CategoryResponse.ok(convertCategories);
    }

    @Override
    public CategoryResponse categorySave(CategoryRequest request) {
        Category category = request.toCategory();
        return CategoryResponse.ok(categoryQueryService.save(category));
    }

    @Override
    public CategoryResponse categoryUpdate(CategoryUpdateRequest request) {
        Category update = categoryQueryService.update(request.getCategoryId(), request.getNewCategoryName());
        return CategoryResponse.ok(CategoryResponse.ok(CategoryDto.toCategory(update)));
    }

    @Override
    public CategoryResponse categoryDelete(Long categoryId) {
        Long delete = categoryQueryService.delete(categoryId);
        return CategoryResponse.ok(CategoryResponse.ok(delete));
    }
}
