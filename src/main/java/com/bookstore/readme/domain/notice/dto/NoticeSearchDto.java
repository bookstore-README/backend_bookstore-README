package com.bookstore.readme.domain.notice.dto;

import com.bookstore.readme.domain.notice.domain.Notice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeSearchDto extends NoticeDto {
    private final Long noticeId;

    public NoticeSearchDto(String title, String content, LocalDateTime createDate, LocalDateTime updateDate, Long noticeId) {
        super(title, content, createDate, updateDate);
        this.noticeId = noticeId;
    }

    public static NoticeSearchDto of(Notice notice) {
        return new NoticeSearchDto(
                notice.getTitle(),
                notice.getContent(),
                notice.getCreateDate(),
                notice.getUpdateDate(),
                notice.getId()
        );
    }
}
