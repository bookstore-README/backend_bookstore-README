package com.bookstore.readme.domain.file.exception;

public class NotEqualFileExt extends FileException {

    private final String fileName;

    public NotEqualFileExt(String fileName) {
        super(FileStatus.NOT_EQUAL_FILE_EXT);
        this.fileName = fileName;
    }

}
