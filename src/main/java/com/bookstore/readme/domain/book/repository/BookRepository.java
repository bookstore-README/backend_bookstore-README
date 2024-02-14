package com.bookstore.readme.domain.book.repository;

import com.bookstore.readme.domain.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    boolean existsByBookTitle(String bookTitle);

    @Query("select b from Book b where b.categories in :categories ORDER by  RAND()")
    Page<Book> findRandomBookPage(List<String> categories, Pageable pageable);
}
