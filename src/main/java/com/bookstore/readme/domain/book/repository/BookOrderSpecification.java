package com.bookstore.readme.domain.book.repository;

import com.bookstore.readme.domain.book.domain.Book;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class BookOrderSpecification {

    //신간
    public static Specification<Book> newestCursor(Long cursorId, LocalDateTime publishDate, boolean ascending) {
        return ((root, query, criteriaBuilder) -> {
            Expression<Long> db = criteriaBuilder.sum(
                    criteriaBuilder.prod(criteriaBuilder.function("UNIX_TIMESTAMP", Long.class, root.get("publishedDate")), 10000L),
                    root.get("id")
            );

            Expression<Long> targetCursorId = criteriaBuilder.sum(
                    criteriaBuilder.prod(criteriaBuilder.function("UNIX_TIMESTAMP", Long.class, criteriaBuilder.literal(publishDate)), 10000L),
                    cursorId
            );

            return ascending ? criteriaBuilder.greaterThanOrEqualTo(db, targetCursorId) : criteriaBuilder.lessThanOrEqualTo(db, targetCursorId);
        });
    }

    //조회수
    public static Specification<Book> viewCursor(Long cursorId, Integer view, boolean ascending) {
        return (root, query, criteriaBuilder) -> {
            Long compareValue = view.longValue() * 10000L + cursorId;

            Expression<Long> entityId = root.get("id");
            Expression<Long> entityPrice = root.get("viewCount");
            Expression<Long> entityValue = criteriaBuilder.prod(entityPrice, 10000L);
            Expression<Long> entityResult = criteriaBuilder.sum(entityId, entityValue);
            return ascending ? criteriaBuilder.greaterThanOrEqualTo(entityResult, compareValue) : criteriaBuilder.lessThanOrEqualTo(entityResult, compareValue);
        };
    }

    //북마크, 찜개수
    public static Specification<Book> bookmarkCursor(Long cursorId, Integer bookmark, boolean ascending) {
        return (root, query, criteriaBuilder) -> {
            Long compareValue = bookmark.longValue() * 100000L + cursorId;
            Expression<Long> entityId = root.get("id");
            Expression<Long> entityBookmark = root.get("bookmarkCount");
            Expression<Long> entityValue = criteriaBuilder.prod(entityBookmark, 100000L);
            Expression<Long> entityResult = criteriaBuilder.sum(entityId, entityValue);
            return ascending ? criteriaBuilder.greaterThanOrEqualTo(entityResult, compareValue) : criteriaBuilder.lessThanOrEqualTo(entityResult, compareValue);
        };
    }

    //판매량
    public static Specification<Book> quantityCursor(Long cursorId, Integer quantity, boolean ascending) {
        return (root, query, criteriaBuilder) -> {
            Long compareValue = quantity.longValue() * 100000L + cursorId;
            Expression<Long> entityId = root.get("id");
            Expression<Long> entityQuantity = root.get("quantityCount");
            Expression<Long> entityValue = criteriaBuilder.prod(entityQuantity, 100000L);
            Expression<Long> entityResult = criteriaBuilder.sum(entityId, entityValue);
            return ascending ? criteriaBuilder.greaterThanOrEqualTo(entityResult, compareValue) : criteriaBuilder.lessThanOrEqualTo(entityResult, compareValue);
        };
    }

    //가격
    public static Specification<Book> priceCursor(Long cursorId, Integer price, boolean ascending) {
        return (root, query, criteriaBuilder) -> {
            Long compareValue = price.longValue() * 100000L + cursorId;

            Expression<Long> entityId = root.get("id");
            Expression<Long> entityPrice = root.get("price");
            Expression<Long> entityValue = criteriaBuilder.prod(entityPrice, 100000L);
            Expression<Long> entityResult = criteriaBuilder.sum(entityId, entityValue);
            return ascending ? criteriaBuilder.greaterThanOrEqualTo(entityResult, compareValue) : criteriaBuilder.lessThanOrEqualTo(entityResult, compareValue);
        };
    }

    //별점
    public static Specification<Book> ratingCursor(Long cursorId, Double rating, boolean ascending) {
        return (root, query, criteriaBuilder) -> {
            Long compareValue = rating.longValue() * 100000L + cursorId;
            Expression<Long> entityId = root.get("id");
            Expression<Long> entityRating = root.get("averageRating");
            Expression<Long> entityValue = criteriaBuilder.prod(entityRating, 100000L);
            Expression<Long> entityResult = criteriaBuilder.sum(entityId, entityValue);
            return ascending ? criteriaBuilder.greaterThanOrEqualTo(entityResult, compareValue) : criteriaBuilder.lessThanOrEqualTo(entityResult, compareValue);
        };
    }

    //리뷰 개수
    public static Specification<Book> reviewCursor(Long cursorId, Integer review, boolean ascending) {
        return (root, query, criteriaBuilder) -> {
            Long compareValue = review.longValue() * 100000L + cursorId;

            Expression<Long> entityId = root.get("id");
            Expression<Long> entityReview = root.get("reviewCount");
            Expression<Long> entityValue = criteriaBuilder.prod(entityReview, 100000L);
            Expression<Long> entityResult = criteriaBuilder.sum(entityId, entityValue);
            return ascending ? criteriaBuilder.greaterThanOrEqualTo(entityResult, compareValue) : criteriaBuilder.lessThanOrEqualTo(entityResult, compareValue);
        };
    }

    public static Specification<Book> idCursor(Long cursorId, boolean ascending) {
        return (root, query, criteriaBuilder) -> {
            return ascending ? criteriaBuilder.greaterThanOrEqualTo(root.get("id"), cursorId) : criteriaBuilder.lessThanOrEqualTo(root.get("id"), cursorId);
        };

    }
}
