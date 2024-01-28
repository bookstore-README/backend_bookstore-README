package com.bookstore.readme.domain.book.repository;

import com.bookstore.readme.domain.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findAllBy(Pageable pageable);

    Page<Book> findAllByIdGreaterThan(Long id, Pageable pageable);

    Page<Book> findAllByIdLessThan(Long id, Pageable pageable);

    Page<Book> findAllByPriceGreaterThanAndIdNot(Integer price, Long id, Pageable pageable);

    Page<Book> findAllByPriceLessThanAndIdNot(Integer price, Long id, Pageable pageable);

    Page<Book> findAllByBookmarkedGreaterThanAndIdNot(Integer bookmarked, Long id, Pageable pageable);

    Page<Book> findAllByBookmarkedLessThanAndIdNot(Integer bookmarked, Long id, Pageable pageable);


    Page<Book> findAllByIdGreaterThanEqual(Long id, Pageable pageable);

    Page<Book> findAllByIdLessThanEqual(Long id, Pageable pageable);

}
