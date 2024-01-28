package com.bookstore.readme.domain.book.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.SortType;
import com.bookstore.readme.domain.book.repository.BookRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
            return bookRepository.findAllBy(pageRequest);

        Book book = bookRepository.findById(bookId.longValue())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품 아이디입니다."));

        if (sortType == SortType.ID) {
            if (ascending) {
                return bookRepository.findAllByIdGreaterThan(bookId.longValue(), pageRequest);
            } else {
                return bookRepository.findAllByIdLessThan(bookId.longValue(), pageRequest);
            }
        } else if (sortType == SortType.PRICE) {
            if (ascending) {
                return bookRepository.findAllByPriceGreaterThanAndIdNot(book.getPrice(), book.getId(), pageRequest);
            } else {
                return bookRepository.findAllByPriceLessThanAndIdNot(book.getPrice(), book.getId(), pageRequest);
            }
        } else if (sortType == SortType.POPULATION) {
            if (ascending) {
                return bookRepository.findAllByBookmarkedGreaterThanAndIdNot(book.getBookmarked(), book.getId(), pageRequest);
            } else {
                return bookRepository.findAllByBookmarkedLessThanAndIdNot(book.getBookmarked(), book.getId(), pageRequest);
            }
        } else {
            throw new IllegalArgumentException("해당하는 SortType이 존재하지 않습니다.");
        }
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    public Long count() {
        return bookRepository.count();
    }
}
