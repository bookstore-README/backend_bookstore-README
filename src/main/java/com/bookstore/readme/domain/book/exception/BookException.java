package com.bookstore.readme.domain.book.exception;

public class BookException extends RuntimeException {
    private final BookStatus bookStatus;

    public BookException(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public int getStatus() {
        return bookStatus.getStatus();
    }

    public String getMessage() {
        return bookStatus.getMessage();
    }
}
