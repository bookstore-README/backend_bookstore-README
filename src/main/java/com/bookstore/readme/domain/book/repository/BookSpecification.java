package com.bookstore.readme.domain.book.repository;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.SortType;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> pagination(Book book, SortType sortType, boolean ascending, String category) {
        Specification<Book> pagination = pagination(book, sortType, ascending);
        return Specification.where(pagination)
                .and(nameContains(category));
    }

    public static Specification<Book> pagination(Book book, SortType sortType, boolean ascending) {
        Specification<Book> query = idPaging(book.getId(), ascending);

        if (sortType == SortType.PRICE) {
            query = pricePaging(book.getPrice() * 1000 + book.getId(), ascending);
        } else if (sortType == SortType.POPULATION) {
            //TODO BOOK 엔티티에 조회수 컬럼 추가 필요
            query = bookmarkCountPaging(book.getBookmarkCount() * 1000 + book.getId(), ascending);
        } else if (sortType == SortType.VIEW) {
            //TODO BOOK 엔티티에 조회수 컬럼 추가 필요
            query = viewCountPaging(book.getReviewCount() * 1000 + book.getId(), ascending);
        } else if (sortType == SortType.REVIEW) {
            query = reviewCountPaging(book.getReviewCount() * 1000 + book.getId(), ascending);
        } else if (sortType == SortType.STAR) {
            query = averageRatingPaging(book.getAverageRating() * 1000 + book.getId(), ascending);
        }

        return query;
    }

    private static Specification<Book> idPaging(Long id, boolean ascending) {
        return (root, query, criteriaBuilder) ->
                ascending ? criteriaBuilder.greaterThanOrEqualTo(root.get("id"), id) : criteriaBuilder.lessThanOrEqualTo(root.get("id"), id);
    }

    private static Specification<Book> pricePaging(Long price, boolean ascending) {
        return (root, query, criteriaBuilder) -> {
            Expression<Long> id = root.get("id");
            Expression<Long> rating = root.get("price");
            Expression<Long> multipliedValue = criteriaBuilder.prod(rating, 1000L);
            Expression<Long> result = criteriaBuilder.sum(id, multipliedValue);
            return ascending ? criteriaBuilder.greaterThanOrEqualTo(result, price) : criteriaBuilder.lessThanOrEqualTo(result, price);
        };
    }

    private static Specification<Book> bookmarkCountPaging(Long bookmarkCount, boolean ascending) {
        return (root, query, criteriaBuilder) -> {
            Expression<Long> id = root.get("id");
            Expression<Long> rating = root.get("bookmarkCount");
            Expression<Long> multipliedValue = criteriaBuilder.prod(rating, 1000L);
            Expression<Long> result = criteriaBuilder.sum(id, multipliedValue);
            return ascending ? criteriaBuilder.greaterThanOrEqualTo(result, bookmarkCount) : criteriaBuilder.lessThanOrEqualTo(result, bookmarkCount);
        };
    }

    private static Specification<Book> reviewCountPaging(Long reviewCount, boolean ascending) {
        return (root, query, criteriaBuilder) -> {
            Expression<Long> id = root.get("id");
            Expression<Long> rating = root.get("reviewCount");
            Expression<Long> multipliedValue = criteriaBuilder.prod(rating, 1000L);
            Expression<Long> result = criteriaBuilder.sum(id, multipliedValue);
            return ascending ? criteriaBuilder.greaterThanOrEqualTo(result, reviewCount) : criteriaBuilder.lessThanOrEqualTo(result, reviewCount);
        };
    }

    private static Specification<Book> averageRatingPaging(double averageRating, boolean ascending) {
        return (root, query, criteriaBuilder) -> {
            Expression<Double> id = root.get("id");
            Expression<Double> rating = root.get("averageRating");
            Expression<Double> multipliedValue = criteriaBuilder.prod(rating, 1000.0);
            Expression<Double> result = criteriaBuilder.sum(id, multipliedValue);
            return ascending ? criteriaBuilder.greaterThanOrEqualTo(result, averageRating) : criteriaBuilder.lessThanOrEqualTo(result, averageRating);
        };
    }

    private static Specification<Book> viewCountPaging(Long viewCount, boolean ascending) {
        return (root, query, criteriaBuilder) -> {
            Expression<Long> id = root.get("id");
            Expression<Long> rating = root.get("viewCount");
            Expression<Long> multipliedValue = criteriaBuilder.prod(rating, 1000L);
            Expression<Long> result = criteriaBuilder.sum(id, multipliedValue);
            return ascending ? criteriaBuilder.greaterThanOrEqualTo(result, viewCount) : criteriaBuilder.lessThanOrEqualTo(result, viewCount);
        };
    }

    private static Specification<Book> nameContains(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("categories"), "%" + keyword + "%");
    }
}
