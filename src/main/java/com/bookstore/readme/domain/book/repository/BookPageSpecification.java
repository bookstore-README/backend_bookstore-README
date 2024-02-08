package com.bookstore.readme.domain.book.repository;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.SortType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class BookPageSpecification {
    public static Specification<Book> of(Book book, SortType sortType, boolean ascending) {
        if (sortType == SortType.STAR) {
            return BookOrderSpecification.ratingCursor(book.getId(), book.getAverageRating(), ascending);
        } else if (sortType == SortType.REVIEW) {
            return BookOrderSpecification.reviewCursor(book.getId(), book.getReviewCount(), ascending);
        } else if (sortType == SortType.VIEW) {
            return BookOrderSpecification.viewCursor(book.getId(), book.getViewCount(), ascending);
        } else if (sortType == SortType.POPULATION) {
            return BookOrderSpecification.viewCursor(book.getId(), book.getViewCount(), ascending);
        } else if (sortType == SortType.PRICE) {
            return BookOrderSpecification.priceCursor(book.getId(), book.getPrice(), ascending);
        } else if (sortType == SortType.NEWEST) {
            return BookOrderSpecification.newestCursor(book.getId(), book.getPublishedDate(), ascending);
        } else if (sortType == SortType.BESTSELLER) {
            return BookOrderSpecification.bookmarkCursor(book.getId(), book.getBookmarkCount(), ascending);
        } else {
            return BookOrderSpecification.idCursor(book.getId(), ascending);
        }
    }

    public static Specification<Book> of(Book book, SortType sortType, boolean ascending, String search) {
        Specification<Book> specification = Specification.where(BookLikeSpecification.likeAuthorsAndBookTitle(search));
        return specification.and(of(book, sortType, ascending));
    }

    public static Specification<Book> of(Book book, SortType sortType, boolean ascending, List<String> category) {
        Specification<Book> specification = Specification.where(BookLikeSpecification.likeCategories(category));
        return specification.and(of(book, sortType, ascending));
    }
}
