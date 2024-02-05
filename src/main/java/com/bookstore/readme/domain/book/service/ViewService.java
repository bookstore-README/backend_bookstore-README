package com.bookstore.readme.domain.book.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ViewService {
    private final BookRepository bookRepository;

    @Transactional
    public int addViewCount(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundBookByIdException(bookId));
        book.addViewCount();
        return book.getViewCount();
    }
}
