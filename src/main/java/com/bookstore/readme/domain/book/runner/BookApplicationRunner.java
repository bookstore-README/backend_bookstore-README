package com.bookstore.readme.domain.book.runner;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.review.domain.Review;
import com.bookstore.readme.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

@Profile("default")
@Component
@RequiredArgsConstructor
public class BookApplicationRunner implements ApplicationRunner {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final static Random random = new Random();

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        for (int i = 1; i <= 1000; i++) {

            Book book = createBook(i);
            bookRepository.save(book);

            Review review = createReview(i);
            review.changeBook(book);
            reviewRepository.save(review);
        }
    }

    private static Review createReview(int i) {
        return Review.builder()
                .title("리뷰 제목 " + i)
                .content("리뷰 내용 " + i)
                .build();
    }

    private static Book createBook(int i) {

        return Book.builder()
                .bookTitle("책 제목 " + i)
                .publishedDate(generateRandomDate("2011-01-01", "2022-12-31"))
                .bookImgUrl("https://cdn.pixabay.com/photo/2018/01/03/09/09/book-3057902_1280.png")
                .authors("작가1,작가2,작가3")
                .description("여기는 설명 칸 " + i)
                .categories(random.nextInt(1000, 1000000) % 2 == 0 ? "국내도서" : "외국도서")
                .bookmarkCount(0)
                .reviewCount(random.nextInt(1000, 1000000))
                .viewCount(random.nextInt(1000, 1000000))
                .averageRating(Math.floor(random.nextDouble(0, 5)))
                .price(random.nextInt(1000, 1000000))
                .publisher("퍼블리셔")
                .build();
    }

    private static LocalDateTime generateRandomDate(String startDate, String endDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);

            long startTime = start.getTime();
            long endTime = end.getTime();

            long randomTime = startTime + (long) (Math.random() * (endTime - startTime));
            Date randomDate = new Date(randomTime);

            return randomDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
