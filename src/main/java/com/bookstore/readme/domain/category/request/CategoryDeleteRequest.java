package com.bookstore.readme.domain.category.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CategoryDeleteRequest {
    @NotNull(message = "카테고리 아이디를 입력해 주세요")
    private Long categoryId;
}
