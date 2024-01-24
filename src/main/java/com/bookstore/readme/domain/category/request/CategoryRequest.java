package com.bookstore.readme.domain.category.request;

import com.bookstore.readme.domain.category.domain.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CategoryRequest {
    @NotEmpty(message = "카테고리 이름을 입력해 주세요")
    @Pattern(
            regexp = "([a-zA-Z0-9]+>){1,5}[a-zA-Z0-9]+",
            message = "카테고리 이름의 형식은 문자열로 시작하고, 구분자는 > 문자이며, 끝은 문자열로 끝나야 합니다."
    )
    private String categoryName;

    public Category toCategory() {
        return Category.builder()
                .categoryName(categoryName)
                .build();
    }
}
