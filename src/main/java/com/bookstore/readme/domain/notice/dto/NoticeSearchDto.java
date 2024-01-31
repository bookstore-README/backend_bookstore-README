package com.bookstore.readme.domain.notice.dto;

import com.bookstore.readme.domain.notice.domain.Notice;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeSearchDto {
    private final String title;
    private final String content;

    @Builder
    public NoticeSearchDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static NoticeSearchDto of(Notice notice) {
        return NoticeSearchDto.builder()
                .title(notice.getTitle())
                .content(notice.getContent())
                .build();
    }
}
