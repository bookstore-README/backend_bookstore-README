package com.bookstore.readme.domain.book.request;

import com.bookstore.readme.domain.book.dto.SortType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public class BookPageRequest {
    @Schema(description = "현재 조회를 시작할 도서 아이디입니다. 처음 조회시 0입력", example = "0")
    private final Integer bookId;
    @Schema(description = "페이지당 가져올 데이터 수 입니다.", example = "10")
    private final Integer limit;
    @Schema(description = "정렬 기준입니다(STAR REVIEW VIEW POPULATION PRICE NEWEST BESTSELLER ID)", example = "STAR")
    private final List<SortType> sort;
    @Schema(description = "정렬 오름차(true) 내림차(false) 기준입니다.", example = "true")
    private final Boolean ascending;
    @Schema(description = "검색어", example = "이건 검색어!")
    private final String search;

    public BookPageRequest(Integer bookId, Integer limit, List<String> sort, Boolean ascending, String search) {
        this.bookId = bookId == null ? 0 : bookId;
        this.limit = limit == null ? 10 : limit;
        this.sort = (sort == null || sort.isEmpty()) ? List.of(SortType.VIEW) : sort.stream().map(s -> SortType.valueOf(s.toUpperCase())).collect(Collectors.toList());
        this.ascending = ascending != null && ascending;
        this.search = StringUtils.hasText(search) ? "%" + search + "%" : null;
    }
}
