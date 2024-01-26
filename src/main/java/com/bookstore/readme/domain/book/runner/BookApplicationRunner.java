package com.bookstore.readme.domain.book.runner;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.book.service.BookQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class BookApplicationRunner implements ApplicationRunner {

    private final BookRepository bookRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Random random = new Random();
        List<Book> books = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            Book book = Book.builder()
                    .bookTitle("책 제목 " + i)
                    .views(i)
                    .publishedDate("20110223")
                    .bookImgUrl("https://cdn.pixabay.com/photo/2018/01/03/09/09/book-3057902_1280.png")
                    .authors("작가1,작가2,작가3")
                    .description("여기는 설명 칸 " + i)
                    .categories("대분류,중분류")
                    .bookmarked("")
                    .averageRating(Math.floor(random.nextDouble(0, 5)))
                    .build();

            books.add(book);
        }
        bookRepository.saveAll(books);
    }
}
