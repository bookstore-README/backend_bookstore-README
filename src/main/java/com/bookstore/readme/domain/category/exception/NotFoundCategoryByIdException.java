package com.bookstore.readme.domain.category.exception;

import lombok.Getter;

@Getter
public class NotFoundCategoryByIdException extends CategoryException {
    private final Long categoryId;

    public NotFoundCategoryByIdException(Long categoryId) {
        super(CategoryStatus.NOT_FOUND_CATEGORY_BY_ID);
        this.categoryId = categoryId;
    }
}
