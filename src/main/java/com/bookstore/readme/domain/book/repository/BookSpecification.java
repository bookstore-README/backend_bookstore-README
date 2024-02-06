package com.bookstore.readme.domain.book.repository;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.SortType;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> singleSortAndCategoryPagination(Book book, SortType sortType, boolean ascending, String category) {
        Specification<Book> pagination = singleSortPagination(book, sortType, ascending);
        return Specification.where(pagination)
                .and(nameContains(category));
    }

    public static Specification<Book> singleSortPagination(Book book, SortType sortType, boolean ascending) {
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
            cursorId = (long) (book.getAverageRating() * 100000 + book.getId());
        } else if (sortType == SortType.ID) {
            cursorId = book.getId();
        }

        return singleSortPagination(sortType, cursorId, ascending);
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


    private static Specification<Book> nameContains(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("categories"), "%" + keyword + "%");
    }
}
