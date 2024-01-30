package com.bookstore.readme.domain.book.request;

import com.bookstore.readme.domain.book.dto.SortType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BookPageRequest {
    @Schema(description = "현재 조회를 시작할 도서 아이디입니다.", example = "1")
    private final Integer bookId;
    @Schema(description = "페이지당 가져올 데이터 수 입니다.", example = "10")
    private final Integer limit;
    @Schema(description = "정렬 기준입니다(PRICE, POPULATION, ID).", example = "PRICE")
    private final SortType sort;
    @Schema(description = "정렬 오름차(true) 내림차(false) 기준입니다.", example = "true")
    private final Boolean ascending;

    public BookPageRequest(Integer bookId, Integer limit, SortType sort, Boolean ascending) {
        this.bookId = bookId;
        this.limit = (limit != null) ? limit : 10;
        this.sort = (sort != null) ? sort : SortType.ID;
        this.ascending = (ascending != null) ? ascending : true;
    }

    public Sort convertSort() {
        String[] split = sort.getSortType().split(",");
        List<Sort.Order> orders = new ArrayList<>();

        for (String sort : split) {
            orders.add(createOrder(sort));
        }

        return Sort.by(orders);
    }

    private Sort.Order createOrder(String sortType) {
        if (sortType.equals("id"))
            return Sort.Order.asc("id");

        return ascending ? Sort.Order.asc(sortType) : Sort.Order.desc(sortType);
    }
}
