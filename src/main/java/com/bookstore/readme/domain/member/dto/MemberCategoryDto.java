package com.bookstore.readme.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberCategoryDto {
    @Schema(description = "선택한 선호 장르")
    private List<Integer> categories;
}
