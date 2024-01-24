package com.bookstore.readme.domain.category.exception;

import lombok.Getter;

@Getter
public class DuplicationCategoryNameException extends CategoryException {
    private final String categoryName;

    public DuplicationCategoryNameException(String categoryName) {
        super(CategoryStatus.DUPLICATE_CATEGORY_NAME);
        this.categoryName = categoryName;
    }
}
