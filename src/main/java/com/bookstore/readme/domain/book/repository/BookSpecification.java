package com.bookstore.readme.domain.book.repository;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.SortType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class BookSpecification {
    public static Specification<Book> pagination(Book book, List<SortType> sortTypes, boolean ascending) {
        Specification<Book> query = ascending ? idGreaterThan(book.getId()) : idLessThanOrEqualTo(book.getId());

        SortType sortType = sortTypes.get(0);
        if (sortType == SortType.PRICE) {
            query = ascending ? priceGreaterThanOrEqualTo(book.getPrice()) : priceLessThanOrEqualTo(book.getPrice());
        } else if (sortType == SortType.POPULATION) {
            query = ascending ? bookmarkCountGreaterThanOrEqualTo(book.getBookmarkCount()) : bookmarkCountLessThanOrEqualTo(book.getBookmarkCount());
        }

        return query;
    }

    public static Specification<Book> pagination(SortType sortType, boolean ascending, Book book) {
        return switch (sortType) {
            case ID -> ascending ? idGreaterThan(book.getId()) : idLessThanOrEqualTo(book.getId());
            case PRICE ->
                    ascending ? bookmarkCountGreaterThanOrEqualTo(book.getBookmarkCount()) : bookmarkCountLessThanOrEqualTo(book.getBookmarkCount());
            case POPULATION ->
                    ascending ? bookmarkedGreaterThanAndIdNot(book.getBookmarkCount(), book.getId()) : bookmarkedLessThanAndIdNot(book.getBookmarkCount(), book.getId());
            default -> throw new IllegalArgumentException("해당하는 SortType이 존재하지 않습니다.");
        };
    }

    private static Specification<Book> idGreaterThan(Long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("id"), id);
    }

    private static Specification<Book> idLessThanOrEqualTo(Long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("id"), id);
    }

    private static Specification<Book> priceGreaterThanAndIdNot(double price, Long id) {
        return Specification
                .where(priceGreaterThanOrEqualTo(price))
                .and(idNotEqual(id));
    }

    private static Specification<Book> priceLessThanAndIdNot(double price, Long id) {
        return Specification
                .where(priceLessThanOrEqualTo(price))
                .and(idNotEqual(id));
    }

    private static Specification<Book> bookmarkedGreaterThanAndIdNot(int bookmarked, Long id) {
        return Specification
                .where(bookmarkCountGreaterThanOrEqualTo(bookmarked))
                .and(idNotEqual(id));
    }

    private static Specification<Book> bookmarkedLessThanAndIdNot(int bookmarked, Long id) {
        return Specification
                .where(bookmarkCountLessThanOrEqualTo(bookmarked))
                .and(idNotEqual(id));
    }

    private static Specification<Book> priceGreaterThanOrEqualTo(double price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    private static Specification<Book> priceLessThanOrEqualTo(double price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    private static Specification<Book> bookmarkCountGreaterThanOrEqualTo(int bookmark_count) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("bookmark_count"), bookmark_count);
    }

    private static Specification<Book> bookmarkCountLessThanOrEqualTo(int bookmark_count) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("bookmark_count"), bookmark_count);
    }

    private static Specification<Book> idNotEqual(Long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("id"), id);
    }
}
