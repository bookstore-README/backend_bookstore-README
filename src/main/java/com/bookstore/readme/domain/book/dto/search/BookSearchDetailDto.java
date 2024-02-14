package com.bookstore.readme.domain.book.dto.search;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.bookmark.dto.BookmarkDto;
import com.bookstore.readme.domain.review.dto.ReviewSearchDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@SuperBuilder
public class BookSearchDetailDto extends BookDto {
    private List<BookmarkDto> bookmarks;
    private List<ReviewSearchDto> reviews;
    private List<Integer> ratingDist;

    public BookSearchDetailDto(Long bookId, String bookTitle, LocalDateTime publishedDate, String bookImgUrl, List<String> authors, String description, List<String> categories, Double averageRating, Integer price, Integer bookmarkCount, Integer reviewCount, Integer viewCount, Integer quantityCount, String publisher, LocalDateTime createDate, LocalDateTime updateDate, List<BookmarkDto> bookmarks, List<ReviewSearchDto> reviews, List<Integer> ratingDist) {
        super(bookId, bookTitle, publishedDate, bookImgUrl, authors, description, categories, averageRating, price, bookmarkCount, reviewCount, viewCount, quantityCount, publisher, createDate, updateDate);
        this.bookmarks = bookmarks;
        this.reviews = reviews;
        this.ratingDist = ratingDist;
    }

    public static BookSearchDetailDto of(Book book, List<ReviewSearchDto> reviews, List<BookmarkDto> bookmarks) {
        return BookSearchDetailDto.builder()
                .bookId(book.getId())
                .bookTitle(book.getBookTitle())
                .publishedDate(book.getPublishedDate())
                .bookImgUrl(book.getBookImgUrl())
                .authors(convertAuthors(book.getAuthors()))
                .description(book.getDescription())
                .categories(convertCategories(book.getCategories()))
                .averageRating(book.getAverageRating())
                .ratingDist(convertRating(reviews))
                .price(book.getPrice())
                .bookmarks(bookmarks)
                .reviews(reviews)
                .bookmarkCount(book.getBookmarkCount())
                .reviewCount(book.getReviewCount())
                .viewCount(book.getViewCount())
                .quantityCount(book.getQuantityCount())
                .publisher(book.getPublisher())
                .createDate(book.getCreateDate())
                .updateDate(book.getUpdateDate())
                .build();
    }

    private static List<Integer> convertRating(List<ReviewSearchDto> reviews) {
        Integer[] test = new Integer[]{0,0,0,0,0};
        for (ReviewSearchDto review : reviews) {
            if (!(review.getReviewRating() == null || review.getReviewRating() == 0))
                test[(int) (review.getReviewRating() - 1)] += 1;
        }

        return List.of(test);
    }

}
