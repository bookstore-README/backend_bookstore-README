package com.bookstore.readme.domain.notice.dto;

import com.bookstore.readme.domain.notice.domain.Notice;
import lombok.Getter;

@Getter
public class NoticeSearchDto extends NoticeDto {

    public NoticeSearchDto(String title, String content) {
        super(title, content);
    }

    public static NoticeSearchDto of(Notice notice) {
        return new NoticeSearchDto(notice.getTitle(), notice.getContent());
    }
}
