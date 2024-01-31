package com.bookstore.readme.domain.notice.exception;

public class NoticeException extends RuntimeException {
    private final NoticeStatus noticeStatus;

    public NoticeException(NoticeStatus noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public int getStatus() {
        return noticeStatus.getStatus();
    }

    public String getMessage() {
        return noticeStatus.getMessage();
    }
}
