package com.bookstore.readme.domain.category.exception;

public class CategoryException extends RuntimeException {
    private final CategoryStatus categoryStatus;

    public CategoryException(CategoryStatus categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public int getStatus() {
        return categoryStatus.getStatus();
    }

    public String getMessage() {
        return categoryStatus.getMessage();
    }
}
