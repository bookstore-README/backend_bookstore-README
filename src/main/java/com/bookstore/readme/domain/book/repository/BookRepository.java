package com.bookstore.readme.domain.book.repository;

import com.bookstore.readme.domain.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAllByIdGreaterThanEqual(Long id, Pageable pageRequest);

    Page<Book> findAllByIdLessThanEqual(Long id, Pageable pageRequest);

}
