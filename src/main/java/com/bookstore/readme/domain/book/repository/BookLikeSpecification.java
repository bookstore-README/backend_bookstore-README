package com.bookstore.readme.domain.book.repository;

import com.bookstore.readme.domain.book.domain.Book;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class BookLikeSpecification {
    public static Specification<Book> likeCategories(List<String> categories) {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = new Predicate[categories.size()];
            for (int i = 0; i < categories.size(); i++) {
                predicates[i] = criteriaBuilder.like(root.get("categories"), "%" + categories.get(i) + "%");
            }
            return criteriaBuilder.or(predicates);
        };
    }

    public static Specification<Book> likeAuthorsAndBookTitle(String search) {
        return Specification.where(likeAuthors(search)).or(likeBookTitle(search));
    }

    public static Specification<Book> likeCategory(String category) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("categories"), "%" + category + "%");
        });
    }

    public static Specification<Book> likeAuthors(String authors) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("authors"), authors);
        });
    }

    public static Specification<Book> likeBookTitle(String bookTitle) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("bookTitle"), bookTitle);
        });
    }


}
