package com.bookstore.readme.domain.notice.exception;

import lombok.Getter;

@Getter
public class NotFoundNoticeByIdException extends NoticeException {
    private final Long noticeId;

    public NotFoundNoticeByIdException(Long noticeId) {
        super(NoticeStatus.NOT_FOUND_NOTICE_BY_ID);
        this.noticeId = noticeId;
    }
}
