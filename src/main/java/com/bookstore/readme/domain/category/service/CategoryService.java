package com.bookstore.readme.domain.category.service;

import com.bookstore.readme.domain.category.request.CategoryRequest;
import com.bookstore.readme.domain.category.request.CategoryUpdateRequest;
import com.bookstore.readme.domain.category.response.CategoryResponse;

public interface CategoryService {
    CategoryResponse categoryList();

    CategoryResponse categorySave(CategoryRequest request);

    CategoryResponse categoryUpdate(CategoryUpdateRequest request);

    CategoryResponse categoryDelete(Long categoryId);
}
