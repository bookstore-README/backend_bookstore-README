package com.bookstore.readme.domain.book.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class FavoriteCategoryRequest {
    @Schema(defaultValue = "0", description = "isRandom 값이 true일 경우 필수 입력 값")
    private final List<Integer> categoryId;

    @Schema(description = "맞춤 도서 100권 조회면 false, 아니면 true")
    private final Boolean isRandom;


    public FavoriteCategoryRequest(List<Integer> categoryId, Boolean isRandom) {
        this.categoryId = categoryId == null || categoryId.isEmpty() ? new ArrayList<>() : categoryId;
        this.isRandom = isRandom != null && isRandom;
    }
}
