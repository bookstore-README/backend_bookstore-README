package com.bookstore.readme.domain.notice.dto;

import com.bookstore.readme.domain.notice.domain.Notice;
import lombok.Getter;

@Getter
public class NoticeListDto extends NoticeDto {
    private final Long noticeId;

    public NoticeListDto(Long noticeId, String title, String content) {
        super(title, content);
        this.noticeId = noticeId;
    }

    public static NoticeListDto of(Notice notice) {
        return new NoticeListDto(notice.getId(), notice.getTitle(), notice.getContent());
    }
}
