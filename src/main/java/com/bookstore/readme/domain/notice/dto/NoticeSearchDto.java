package com.bookstore.readme.domain.notice.dto;

import com.bookstore.readme.domain.notice.domain.Notice;
import lombok.Getter;

@Getter
public class NoticeSearchDto extends NoticeDto {
    private final Long noticeId;

    public NoticeSearchDto(Long noticeId, String title, String content) {
        super(title, content);
        this.noticeId = noticeId;
    }

    public static NoticeSearchDto of(Notice notice) {
        return new NoticeSearchDto(notice.getId(), notice.getTitle(), notice.getContent());
    }
}
