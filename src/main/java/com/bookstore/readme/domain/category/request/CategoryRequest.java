package com.bookstore.readme.domain.category.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoryRequest {
    private final Long mainId;
    private final List<String> categories;

    public CategoryRequest(Long mainId, List<String> categories) {
        this.mainId = mainId;
        this.categories = categories;
    }
}