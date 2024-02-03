package com.bookstore.readme.domain.book.request;

import com.bookstore.readme.domain.book.dto.SortType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class BookPageRequest {
    @Schema(description = "현재 조회를 시작할 도서 아이디입니다.", example = "1")
    private final Integer bookId;
    @Schema(description = "페이지당 가져올 데이터 수 입니다.", example = "10")
    private final Integer limit;
    @Schema(description = "정렬 기준입니다(PRICE, POPULATION, ID). 다중 정렬 가능", example = "PRICE,POPULATION")
    private final List<SortType> sort = new ArrayList<>();
    @Schema(description = "정렬 오름차(true) 내림차(false) 기준입니다.", example = "true")
    private final Boolean ascending;

    public BookPageRequest(Integer bookId, Integer limit, List<SortType> sort, Boolean ascending) {
        this.bookId = bookId;
        this.limit = limit == null ? 10 : limit;
        if (sort != null)
            this.sort.addAll(sort);

        this.ascending = ascending == null || ascending;
    }
}
