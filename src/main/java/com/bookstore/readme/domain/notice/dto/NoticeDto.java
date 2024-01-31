package com.bookstore.readme.domain.notice.dto;

import com.bookstore.readme.domain.notice.domain.Notice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeDto {
    private final String title;
    private final String content;

    private final LocalDateTime createDate;
    private final LocalDateTime updateDate;

    public NoticeDto(String title, String content, LocalDateTime createDate, LocalDateTime updateDate) {
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public static NoticeDto of(Notice notice) {
        return new NoticeDto(
                notice.getTitle(),
                notice.getContent(),
                notice.getCreateDate(),
                notice.getUpdateDate()
        );
    }
}
