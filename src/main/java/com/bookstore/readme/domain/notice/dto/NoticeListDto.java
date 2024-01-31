package com.bookstore.readme.domain.notice.dto;

import com.bookstore.readme.domain.notice.domain.Notice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeListDto extends NoticeDto {
    private final Long noticeId;

    public NoticeListDto(String title, String content, LocalDateTime createDate, LocalDateTime updateDate, Long noticeId) {
        super(title, content, createDate, updateDate);
        this.noticeId = noticeId;
    }

    public static NoticeListDto of(Notice notice) {
        return new NoticeListDto(
                notice.getTitle(),
                notice.getContent(),
                notice.getCreateDate(),
                notice.getUpdateDate(),
                notice.getId()
        );
    }
}
