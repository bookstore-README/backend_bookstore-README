package com.bookstore.readme.domain.book.repository;

import com.bookstore.readme.domain.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    boolean existsByBookTitle(String bookTitle);

    Page<Book> findAllBy(Pageable pageable);

    Page<Book> findAllByIdGreaterThanEqual(Long id, Pageable pageable);

    Page<Book> findAllByIdLessThanEqual(Long id, Pageable pageable);

}
