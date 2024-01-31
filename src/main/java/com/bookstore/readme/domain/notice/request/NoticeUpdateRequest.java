package com.bookstore.readme.domain.notice.request;

import com.bookstore.readme.domain.notice.domain.Notice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class NoticeUpdateRequest {
    @Schema(description = "게시글의 제목입니다.", example = "게시글 제목 입니다.")
    private String title;

    @Length(max = 255, message = "255자 이하로 입력해주세요.")
    @Schema(description = "게시글의 내용입니다.", example = "게시글 내용 입니다.(문자열, 255자 이하)")
    private String content;

    public Notice toNotice() {
        return Notice.builder()
                .title(title)
                .content(content)
                .build();
    }
}
