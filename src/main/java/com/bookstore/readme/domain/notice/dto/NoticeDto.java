package com.bookstore.readme.domain.notice.dto;

import com.bookstore.readme.domain.notice.domain.Notice;
import lombok.Getter;

@Getter
public class NoticeDto {
    private final String title;
    private final String content;

    public NoticeDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static NoticeDto of(Notice notice) {
        return new NoticeDto(notice.getTitle(), notice.getContent());
    }
}
