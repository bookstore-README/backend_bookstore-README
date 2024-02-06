package com.bookstore.readme.domain.category.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryDto {
    private final List<CategoryInfo> domestic;
    private final List<CategoryInfo> foreign;

    @Builder
    public CategoryDto(List<CategoryInfo> domestic, List<CategoryInfo> foreign) {
        this.domestic = domestic;
        this.foreign = foreign;
    }
}
