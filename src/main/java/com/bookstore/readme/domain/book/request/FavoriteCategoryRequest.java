package com.bookstore.readme.domain.book.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class FavoriteCategoryRequest {
    @Schema(defaultValue = "0")
//    @NotEmpty(message = "카테고리 아이디는 필수 입력 값입니다.")
    private final List<Integer> categoryId;

    public FavoriteCategoryRequest(List<Integer> categoryId) {
        this.categoryId = categoryId == null || categoryId.isEmpty() ? null : categoryId;
    }
}
