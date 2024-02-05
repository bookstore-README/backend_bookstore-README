package com.bookstore.readme.domain.book.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.book.request.BookRequest;
import com.bookstore.readme.domain.book.response.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookSaveService {
    private final BookRepository bookRepository;

    @Transactional
    public BookResponse bookSave(BookRequest request) {
        Book book = request.toBook();
        bookRepository.save(book);
        return BookResponse.ok(book.getId());
    }
}
