package com.bookstore.readme.domain.book.service.page;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.SortType;
import com.bookstore.readme.domain.book.dto.page.BookDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public abstract class BookPage {

    protected static Sort getSort(SortType sortType, boolean ascending) {
        Sort orders = ascending ? Sort.by(Sort.Order.asc(sortType.getSortType())) : Sort.by(Sort.Order.desc(sortType.getSortType()));

        if (sortType != SortType.ID) {
            orders = ascending ? Sort.by(Sort.Order.asc(sortType.getSortType()), Sort.Order.asc(SortType.ID.getSortType())) : Sort.by(Sort.Order.desc(sortType.getSortType()), Sort.Order.desc(SortType.ID.getSortType()));
//            orders.and(Sort.by(ascending ? Sort.Order.asc(SortType.ID.getSortType()) : Sort.Order.desc(SortType.ID.getSortType())));
        }

        return orders;
    }
}
