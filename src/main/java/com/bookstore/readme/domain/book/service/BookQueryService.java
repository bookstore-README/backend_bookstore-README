package com.bookstore.readme.domain.book.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.repository.BookRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookQueryService {
    private final BookRepository bookRepository;
    private final EntityManager em;

    @Transactional
    public Book findById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseGet(() -> null);
    }

    @Transactional
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public Page<Book> scrollSearch(Integer bookId, PageRequest pageRequest) {
        return bookRepository.findAllByIdGreaterThanEqualOrderByIdAsc(bookId.longValue(), pageRequest);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    public Long count() {
        return bookRepository.count();
    }
}
