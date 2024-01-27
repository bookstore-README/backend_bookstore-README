package com.bookstore.readme.domain.book.service;

import com.bookstore.readme.domain.book.request.BookPageRequest;
import com.bookstore.readme.domain.book.request.BookRequest;
import com.bookstore.readme.domain.book.response.BookResponse;

public interface BookService {
    BookResponse bookList();

    BookResponse bookList(BookPageRequest request);

    BookResponse bookSave(BookRequest request);
}
