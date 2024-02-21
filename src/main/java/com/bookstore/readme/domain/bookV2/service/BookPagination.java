package com.bookstore.readme.domain.bookV2.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.bookV2.dto.BookInfo;
import com.bookstore.readme.domain.bookV2.dto.BookStatics;
import com.bookstore.readme.domain.bookV2.request.BookPageRequest;
import com.bookstore.readme.domain.bookV2.request.SortType;
import com.bookstore.readme.domain.bookmark.domain.Bookmark;
import com.bookstore.readme.domain.bookmark.dto.BookmarkDto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.List;

public abstract class BookPagination<T> {

    public abstract T bookPage(Long memberId, BookPageRequest request);

    protected static Sort getSort(SortType sortType, boolean ascending) {
        Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortType.getType());
        sort = sort.and(Sort.by(direction, "id"));
        return sort;
    }

    protected static BookInfo convertBook(Book book, BookmarkDto bookmark) {
        return BookInfo.builder()
                .bookId(book.getId())
                .bookTitle(book.getBookTitle())
                .description(book.getDescription())
                .publisher(book.getPublisher())
                .bookImgUrl(book.getBookImgUrl())
                .price(book.getPrice())
                .authors(convertAuthors(book.getAuthors()))
                .categories(convertCategories(book.getCategories()))
                .statics(convertStatics(book))
                .bookmarks(bookmark)
                .publishedDate(book.getPublishedDate())
                .createDate(book.getCreateDate())
                .updateDate(book.getUpdateDate())
                .build();
    }

    protected static BookmarkDto convertBookmark(Bookmark bookmark) {
        return BookmarkDto.builder()
                .bookmarkId(bookmark.getId() == null ? -1 : bookmark.getId())
                .bookId(bookmark.getBook() == null ? -1 : bookmark.getBook().getId())
                .memberId(bookmark.getMember() == null ? -1 : bookmark.getMember().getId())
                .isMarked(bookmark.getIsMarked())
                .build();
    }

    protected static BookStatics convertStatics(Book book) {
        return BookStatics.builder()
                .bookmarkCount(book.getBookmarkCount())
                .reviewCount(book.getReviewCount())
                .viewCount(book.getViewCount())
                .quantityCount(book.getQuantityCount())
                .averageRating(book.getAverageRating())
                .build();
    }


    protected static List<String> convertAuthors(String author) {
        String[] split = author.split(",");
        return List.of(split);
    }

    protected static List<String> convertCategories(String category) {
        String[] split = category.split(",");
        return List.of(split);
    }

    protected static Specification<Book> likeAuthorsAndBookTitle(String search) {
        if (StringUtils.hasText(search))
            return Specification.where(likeAuthors(search)).or(likeBookTitle(search));

        return Specification.where(defaultValue());
    }

    protected static Specification<Book> likeCategory(String category) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("categories"), "%" + category + "%");
        });
    }

    protected static Specification<Book> likeAuthors(String authors) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("authors"), authors);
        });
    }

    protected static Specification<Book> likeBookTitle(String bookTitle) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("bookTitle"), bookTitle);
        });
    }

    protected static Specification<Book> defaultValue() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }
}
