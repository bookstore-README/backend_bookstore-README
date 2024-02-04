package com.bookstore.readme.domain.book.repository;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.SortType;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class BookSpecification {
    public static Specification<Book> pagination(Book book, List<SortType> sortTypes, boolean ascending) {
        Specification<Book> query = ascending ? idGreaterThanOrEqualTo(book.getId()) : idLessThanOrEqualTo(book.getId());

        SortType sortType = sortTypes.get(0);
        if (sortType == SortType.PRICE) {
            query = ascending ? priceGreaterThanOrEqualTo(book.getPrice() * 1000 + book.getId()) : priceLessThanOrEqualTo(book.getPrice() * 1000 + book.getId());
        } else if (sortType == SortType.POPULATION) {
            //TODO BOOK 엔티티에 조회수 컬럼 추가 필요
            query = ascending ? bookmarkCountGreaterThanOrEqualTo(book.getBookmarkCount() * 1000 + book.getId()) : bookmarkCountLessThanOrEqualTo(book.getBookmarkCount() * 1000 + book.getId());
        } else if (sortType == SortType.VIEW) {
            //TODO BOOK 엔티티에 조회수 컬럼 추가 필요
            query = ascending ? viewCountGreaterThanOrEqualTo(book.getReviewCount() * 1000 + book.getId()) : viewCountLessThanOrEqualTo(book.getReviewCount() * 1000 + book.getId());
        } else if (sortType == SortType.REVIEW) {
            query = ascending ? reviewCountGreaterThanOrEqualTo(book.getReviewCount() * 1000 + book.getId()) : reviewCountLessThanOrEqualTo(book.getReviewCount() * 1000 + book.getId());
        } else if (sortType == SortType.STAR) {
            query = ascending ? averageRatingGreaterThanOrEqualTo(book.getAverageRating() * 1000 + book.getId()) : averageRatingLessThanOrEqualTo(book.getAverageRating() * 1000 + book.getId());
        }

        return query;
    }

    //ID
    private static Specification<Book> idGreaterThanOrEqualTo(Long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("id"), id);
    }

    private static Specification<Book> idLessThanOrEqualTo(Long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("id"), id);
    }

    //Price
    private static Specification<Book> priceGreaterThanOrEqualTo(Long price) {
        return (root, query, criteriaBuilder) -> {
            Expression<Long> id = root.get("id");
            Expression<Long> rating = root.get("price");
            Expression<Long> multipliedValue = criteriaBuilder.prod(rating, 1000L);
            Expression<Long> result = criteriaBuilder.sum(id, multipliedValue);
            return criteriaBuilder.greaterThanOrEqualTo(result, price);
        };
    }

    private static Specification<Book> priceLessThanOrEqualTo(Long price) {
        return (root, query, criteriaBuilder) -> {
            Expression<Long> id = root.get("id");
            Expression<Long> rating = root.get("price");
            Expression<Long> multipliedValue = criteriaBuilder.prod(rating, 1000L);
            Expression<Long> result = criteriaBuilder.sum(id, multipliedValue);
            return criteriaBuilder.lessThanOrEqualTo(result, price);
        };
    }

    //Bookmark
    private static Specification<Book> bookmarkCountGreaterThanOrEqualTo(Long bookmarkCount) {
        return (root, query, criteriaBuilder) -> {
            Expression<Long> id = root.get("id");
            Expression<Long> rating = root.get("bookmarkCount");
            Expression<Long> multipliedValue = criteriaBuilder.prod(rating, 1000L);
            Expression<Long> result = criteriaBuilder.sum(id, multipliedValue);
            return criteriaBuilder.greaterThanOrEqualTo(result, bookmarkCount);
        };
//                criteriaBuilder.greaterThanOrEqualTo(root.get("bookmarkCount"), bookmarkCount);
    }

    private static Specification<Book> bookmarkCountLessThanOrEqualTo(Long bookmarkCount) {
        return (root, query, criteriaBuilder) -> {
            Expression<Long> id = root.get("id");
            Expression<Long> rating = root.get("bookmarkCount");
            Expression<Long> multipliedValue = criteriaBuilder.prod(rating, 1000L);
            Expression<Long> result = criteriaBuilder.sum(id, multipliedValue);
            return criteriaBuilder.lessThanOrEqualTo(result, bookmarkCount);
        };
//                criteriaBuilder.lessThanOrEqualTo(root.get("bookmarkCount"), bookmarkCount);
    }

    //Review
    private static Specification<Book> reviewCountGreaterThanOrEqualTo(Long reviewCount) {
        return (root, query, criteriaBuilder) -> {
            Expression<Long> id = root.get("id");
            Expression<Long> rating = root.get("reviewCount");
            Expression<Long> multipliedValue = criteriaBuilder.prod(rating, 1000L);
            Expression<Long> result = criteriaBuilder.sum(id, multipliedValue);
            return criteriaBuilder.greaterThanOrEqualTo(result, reviewCount);
        };
//                criteriaBuilder.greaterThanOrEqualTo(root.get("reviewCount"), reviewCount);
    }

    private static Specification<Book> reviewCountLessThanOrEqualTo(Long reviewCount) {
        return (root, query, criteriaBuilder) -> {
            Expression<Long> id = root.get("id");
            Expression<Long> rating = root.get("reviewCount");
            Expression<Long> multipliedValue = criteriaBuilder.prod(rating, 1000L);
            Expression<Long> result = criteriaBuilder.sum(id, multipliedValue);
            return criteriaBuilder.lessThanOrEqualTo(result, reviewCount);
        };
//                criteriaBuilder.lessThanOrEqualTo(root.get("reviewCount"), reviewCount);
    }

    //Star
    private static Specification<Book> averageRatingGreaterThanOrEqualTo(double averageRating) {
        return (root, query, criteriaBuilder) -> {
            Expression<Double> id = root.get("id");
            Expression<Double> rating = root.get("averageRating");
            Expression<Double> multipliedValue = criteriaBuilder.prod(rating, 1000.0);
            Expression<Double> result = criteriaBuilder.sum(id, multipliedValue);
            return criteriaBuilder.greaterThanOrEqualTo(result, averageRating);
        };
    }

    private static Specification<Book> averageRatingLessThanOrEqualTo(double averageRating) {
        return (root, query, criteriaBuilder) -> {
            Expression<Double> id = root.get("id");
            Expression<Double> rating = root.get("averageRating");
            Expression<Double> multipliedValue = criteriaBuilder.prod(rating, 1000.0);
            Expression<Double> result = criteriaBuilder.sum(id, multipliedValue);
            return criteriaBuilder.lessThanOrEqualTo(result, averageRating);
        };
    }

    //View
    private static Specification<Book> viewCountGreaterThanOrEqualTo(Long viewCount) {
        return (root, query, criteriaBuilder) -> {
            Expression<Long> id = root.get("id");
            Expression<Long> rating = root.get("viewCount");
            Expression<Long> multipliedValue = criteriaBuilder.prod(rating, 1000L);
            Expression<Long> result = criteriaBuilder.sum(id, multipliedValue);
            return criteriaBuilder.greaterThanOrEqualTo(result, viewCount);
        };
//                criteriaBuilder.greaterThanOrEqualTo(root.get("viewCount"), viewCount);
    }

    private static Specification<Book> viewCountLessThanOrEqualTo(Long viewCount) {
        return (root, query, criteriaBuilder) -> {
            Expression<Long> id = root.get("id");
            Expression<Long> rating = root.get("viewCount");
            Expression<Long> multipliedValue = criteriaBuilder.prod(rating, 1000L);
            Expression<Long> result = criteriaBuilder.sum(id, multipliedValue);
            return criteriaBuilder.lessThanOrEqualTo(result, viewCount);
        };
//        criteriaBuilder.lessThanOrEqualTo(root.get("viewCount"), viewCount);
    }

}
