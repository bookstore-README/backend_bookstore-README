package com.bookstore.readme.domain.book.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FavoriteCategoryRequest {
    @Schema(defaultValue = "0")
    private final List<Integer> categoryId;

    @Schema(defaultValue = "0")
    private final Integer limit;


    public FavoriteCategoryRequest(List<Integer> categoryId, Integer limit) {
        this.categoryId = categoryId == null || categoryId.isEmpty() ? new ArrayList<>() : categoryId;
        this.limit = limit == null ? 4 : limit;
    }
}
