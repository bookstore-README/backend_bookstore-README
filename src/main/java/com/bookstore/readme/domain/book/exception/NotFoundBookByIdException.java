package com.bookstore.readme.domain.book.exception;

import lombok.Getter;

@Getter
public class NotFoundBookByIdException extends BookException {
    private final Long bookId;

    public NotFoundBookByIdException(Long bookId) {
        super(BookStatus.NOT_FOUND_BOOK_BY_ID);
        this.bookId = bookId;
    }
}
