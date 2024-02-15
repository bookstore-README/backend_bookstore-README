package com.bookstore.readme.domain.category.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberCategory {
    private final List<CategoryInfo> memberCategory;

    @Builder
    public MemberCategory(List<CategoryInfo> memberCategory) {
        this.memberCategory = memberCategory;
    }
}
