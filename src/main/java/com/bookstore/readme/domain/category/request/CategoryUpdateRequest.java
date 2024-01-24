package com.bookstore.readme.domain.category.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CategoryUpdateRequest {
    @NotNull(message = "카테고리 아이디를 입력해 주세요")
    private Long categoryId;

    @NotEmpty(message = "새로운 카테고리 이름을 입력해 주세요")
    @Pattern(
            regexp = "([a-zA-Z0-9]+>){1,5}[a-zA-Z0-9]+",
            message = "카테고리 이름의 형식은 문자열로 시작하고, 구분자는 > 문자이며, 끝은 문자열로 끝나야 합니다."
    )
    private String newCategoryName;
}
