package com.bookstore.readme.domain.notice.request;

import com.bookstore.readme.domain.notice.domain.Notice;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class NoticeSaveRequest {
    @NotEmpty(message = "제목은 필수로 입력해야합니다.")
    @Schema(description = "게시글의 제목입니다.", example = "title")
    private String title;

    @Length(max = 255, message = "255자 이하로 입력해주세요.")
    @Schema(description = "게시글의 내용입니다.", example = "이것은 게시글 입니다.")
    private String content;

    public Notice toNotice() {
        return Notice.builder()
                .title(title)
                .content(content)
                .build();
    }
}
