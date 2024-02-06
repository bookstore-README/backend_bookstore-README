package com.bookstore.readme.domain.category.dto;

import com.bookstore.readme.domain.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryInfo {
    private final Long categoryId;
    private final Long mainId;
    private final Long subId;
    private final String mainName;
    private final String subName;

    @Builder
    public CategoryInfo(Long categoryId, Long mainId, Long subId, String mainName, String subName) {
        this.categoryId = categoryId;
        this.mainId = mainId;
        this.subId = subId;
        this.mainName = mainName;
        this.subName = subName;
    }

    public static CategoryInfo of(Category category) {
        return CategoryInfo.builder()
                .categoryId(category.getId())
                .mainId(category.getMainId())
                .subId(category.getSubId())
                .mainName(category.getMainName())
                .subName(category.getSubName())
                .build();
    }
}
