package com.bookstore.readme.domain.file.exception;

import lombok.Getter;

@Getter
public enum FileStatus {
    NOT_EQUAL_FILE_EXT(400, "해당");

    private final int status;
    private final String message;

    FileStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
