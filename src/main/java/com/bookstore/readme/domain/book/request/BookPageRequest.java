package com.bookstore.readme.domain.book.request;

import com.bookstore.readme.domain.book.dto.SortType;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BookPageRequest {
    private final Integer bookId;
    private final Integer limit;
    private final SortType sort;
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
