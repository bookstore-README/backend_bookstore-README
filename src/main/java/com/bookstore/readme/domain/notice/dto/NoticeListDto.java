package com.bookstore.readme.domain.notice.dto;

import com.bookstore.readme.domain.notice.domain.Notice;

public class NoticeListDto extends NoticeDto {
    public NoticeListDto(String title, String content) {
        super(title, content);
    }

    public static NoticeListDto of(Notice notice) {
        return new NoticeListDto(notice.getTitle(), notice.getContent());
    }
}
