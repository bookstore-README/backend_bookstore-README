package com.bookstore.readme.domain.book.dto.favorite;

import com.bookstore.readme.domain.category.domain.Category;
import com.bookstore.readme.domain.category.dto.CategoryDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BookPageDto {
    private final Integer total;
    private final Integer limit;
    private final List<BookDto> books;
    private final List<String> memberCategory;

    @Builder
    public BookPageDto(Integer total, Integer limit, List<BookDto> books, List<String> memberCategory) {
        this.total = total;
        this.limit = limit;
        this.books = books;
        this.memberCategory = memberCategory;
    }
}
