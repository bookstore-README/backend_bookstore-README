package com.bookstore.readme.domain.book.repository;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.SortType;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.bookstore.readme.domain.book.repository.BookEqualSpecification.*;
import static com.bookstore.readme.domain.book.repository.BookLikeSpecification.*;

public class BookSpecification {

    public static Specification<Book> defaultSearch(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search != null) {
                return likeAuthorsAndBookTitle(search).toPredicate(root, query, criteriaBuilder);
            } else {
                return null;
            }
        };
    }

    public static Specification<Book> categoryAndSearch(String category, String search) {
        if (StringUtils.hasText(search)) {
            return Specification.where(likeCategory(category))
                    .and(likeAuthorsAndBookTitle(search));
        } else {
            return likeCategory(category);
        }

    }

    public static Specification<Book> singleSortAndCategoryPagination(Book book, SortType sortType, boolean ascending, String category, String search) {
        Specification<Book> pagination = singleSortPagination(book, sortType, ascending, search);

        if (StringUtils.hasText(search)) {
            return Specification.where(pagination)
                    .and(likeCategory(category))
                    .and(likeAuthorsAndBookTitle(search));
        } else {
            return Specification.where(pagination)
                    .and(likeCategory(category));
        }

    }

    public static Specification<Book> singleSortPagination(Book book, SortType sortType, boolean ascending, String search) {
        Long cursorId = null;

        if (sortType == SortType.PRICE) {
            cursorId = book.getPrice() * 1000 + book.getId();
        } else if (sortType == SortType.POPULATION) {
            cursorId = book.getBookmarkCount() * 1000 + book.getId();
        } else if (sortType == SortType.VIEW) {
            cursorId = book.getViewCount() * 1000 + book.getId();
        } else if (sortType == SortType.REVIEW) {
            cursorId = book.getReviewCount() * 1000 + book.getId();
        } else if (sortType == SortType.STAR) {
            cursorId = (long) (book.getAverageRating() * 1000 + book.getId());
        } else if (sortType == SortType.ID) {
            cursorId = book.getId();
        } else if (sortType == SortType.NEWEST) {
            Specification<Book> test = newSort(book, sortType, ascending);
            return Specification
                    .where(test)
                    .or(equalId(book.getId()));
        } else if (sortType == SortType.BESTSELLER) {
            cursorId = book.getBookmarkCount() * 1000 + book.getId();
        }

        Specification<Book> bookSpecification = singleSortPagination(sortType, cursorId, ascending);
        if (StringUtils.hasText(search))
            bookSpecification = Specification.where(bookSpecification)
                    .and(likeAuthorsAndBookTitle(search));

        return bookSpecification;
    }

    private static Specification<Book> singleSortPagination(SortType sortType, Long cursorId, boolean ascending) {
        return (root, query, criteriaBuilder) -> {
            if (sortType == SortType.ID)
                return ascending ? criteriaBuilder.greaterThanOrEqualTo(root.get(sortType.getSortType()), cursorId) : criteriaBuilder.lessThanOrEqualTo(root.get(sortType.getSortType()), cursorId);


            Expression<Long> id = root.get("id");
            Expression<Long> rating = root.get(sortType.getSortType());
            Expression<Long> multipliedValue = criteriaBuilder.prod(rating, 1000L);
            Expression<Long> result = criteriaBuilder.sum(id, multipliedValue);
            return ascending ? criteriaBuilder.greaterThanOrEqualTo(result, cursorId) : criteriaBuilder.lessThanOrEqualTo(result, cursorId);
        };
    }

    private static Specification<Book> newSort(Book book, SortType sortType, boolean ascending) {
        return ((root, query, criteriaBuilder) -> {
            Expression<Long> db = criteriaBuilder.sum(
                    criteriaBuilder.prod(criteriaBuilder.function("UNIX_TIMESTAMP", Long.class, root.get(sortType.getSortType())), 1000L),
                    root.get("id")
            );

            Expression<Long> targetCursorId = criteriaBuilder.sum(
                    criteriaBuilder.prod(criteriaBuilder.function("UNIX_TIMESTAMP", Long.class, criteriaBuilder.literal(book.getPublishedDate())), 1000L),
                    book.getId()
            );
            return ascending ? criteriaBuilder.greaterThanOrEqualTo(db, targetCursorId) : criteriaBuilder.lessThanOrEqualTo(db, targetCursorId);
        });
    }

}
