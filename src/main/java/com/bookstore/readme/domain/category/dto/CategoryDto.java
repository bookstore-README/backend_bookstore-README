package com.bookstore.readme.domain.category.dto;

import com.bookstore.readme.domain.category.domain.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryDto {
    private Long id;
    private String categoryName;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @Builder
    public CategoryDto(Long id, String categoryName, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.categoryName = categoryName;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public static CategoryDto toCategory(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .createDate(category.getCreateDate())
                .updateDate(category.getUpdateDate())
                .build();
    }
}
