package com.bookstore.readme.domain.book.repository;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.SortType;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> pagination(SortType sortType, boolean ascending, Book book) {
        return switch (sortType) {
            case ID -> ascending ? idGreaterThan(book.getId()) : idLessThan(book.getId());
            case PRICE ->
                    ascending ? priceGreaterThanAndIdNot(book.getPrice(), book.getId()) : priceLessThanAndIdNot(book.getPrice(), book.getId());
            case POPULATION ->
                    ascending ? bookmarkedGreaterThanAndIdNot(book.getBookmarked(), book.getId()) : bookmarkedLessThanAndIdNot(book.getBookmarked(), book.getId());
            default -> throw new IllegalArgumentException("해당하는 SortType이 존재하지 않습니다.");
        };
    }

    private static Specification<Book> idGreaterThan(Long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("id"), id);
    }

    private static Specification<Book> idLessThan(Long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("id"), id);
    }

    private static Specification<Book> priceGreaterThanAndIdNot(double price, Long id) {
        return Specification
                .where(priceGreaterThan(price))
                .and(idNotEqual(id));
    }

    private static Specification<Book> priceLessThanAndIdNot(double price, Long id) {
        return Specification
                .where(priceLessThan(price))
                .and(idNotEqual(id));
    }

    private static Specification<Book> bookmarkedGreaterThanAndIdNot(int bookmarked, Long id) {
        return Specification
                .where(bookmarkedGreaterThan(bookmarked))
                .and(idNotEqual(id));
    }

    private static Specification<Book> bookmarkedLessThanAndIdNot(int bookmarked, Long id) {
        return Specification
                .where(bookmarkedLessThan(bookmarked))
                .and(idNotEqual(id));
    }

    private static Specification<Book> priceGreaterThan(double price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("price"), price);
    }

    private static Specification<Book> priceLessThan(double price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("price"), price);
    }

    private static Specification<Book> bookmarkedGreaterThan(int bookmarked) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("bookmarked"), bookmarked);
    }

    private static Specification<Book> bookmarkedLessThan(int bookmarked) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("bookmarked"), bookmarked);
    }

    private static Specification<Book> idNotEqual(Long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("id"), id);
    }
}
