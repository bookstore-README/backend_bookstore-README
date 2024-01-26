package com.bookstore.readme.domain.book.service;

import com.bookstore.readme.domain.book.dto.BookDto;
import com.bookstore.readme.domain.book.request.BookRequest;
import com.bookstore.readme.domain.book.response.BookResponse;

public interface BookService {
    BookResponse bookList();

    BookResponse bookList(Integer bookId, Integer limit);

    BookResponse bookSave(BookRequest request);
}
