package com.bookstore.readme.domain.book.repository;

import com.bookstore.readme.domain.book.domain.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookEqualSpecification {
    public static Specification<Book> equalId(Long cursorId) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("id"), cursorId);
        });
    }
}
