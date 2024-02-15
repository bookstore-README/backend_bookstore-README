package com.bookstore.readme.domain.community.request;

import com.bookstore.readme.domain.community.dto.EmojiInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberEmojiRequest {
    @Schema()
    private List<EmojiRequest> emojis;

    public MemberEmojiRequest() {
    }

    public MemberEmojiRequest(List<EmojiRequest> emojis) {
        this.emojis = emojis;
    }
}
