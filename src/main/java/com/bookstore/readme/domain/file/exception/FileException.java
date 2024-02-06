package com.bookstore.readme.domain.file.exception;

import com.bookstore.readme.domain.member.exception.MemberStatus;

public class FileException extends RuntimeException {

    private final FileStatus fileStatus;

    public FileException(FileStatus fileStatus) {
        this.fileStatus = fileStatus;
    }

    public int getStatus() {
        return fileStatus.getStatus();
    }

    public String getMessage() {
        return fileStatus.getMessage();
    }
}
