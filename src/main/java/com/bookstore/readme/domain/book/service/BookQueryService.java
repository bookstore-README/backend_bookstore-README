package com.bookstore.readme.domain.book.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.SortType;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.book.repository.BookSpecification;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public Page<Book> scrollSearch(Integer bookId, PageRequest pageRequest, boolean ascending) {
        if (ascending)
            return bookRepository.findAllByIdGreaterThanEqual(bookId.longValue(), pageRequest);
        else
            return bookRepository.findAllByIdLessThanEqual(bookId.longValue(), pageRequest);
    }

    @Transactional
    public Page<Book> scroll(Integer bookId, SortType sortType, boolean ascending, PageRequest pageRequest) {
        if (sortType == null)
            throw new IllegalArgumentException("SortType은 Null일 수 없습니다.");

        if (bookId == null)
            return bookRepository.findAll(pageRequest);

        Book book = bookRepository.findById(bookId.longValue())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품 아이디입니다."));

        Specification<Book> pagination = BookSpecification.pagination(sortType, ascending, book);
        return bookRepository.findAll(pagination, pageRequest);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    public Long count() {
        return bookRepository.count();
    }
}
