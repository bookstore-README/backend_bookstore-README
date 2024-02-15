package com.bookstore.readme.domain.community.dto;

import lombok.Getter;

@Getter
public enum EmojiType {
    HEART("heart"),
    SMILE("smile"),
    EXCITED("excited"),
    ANGRY("angry"),
    CRYING("crying");

    private final String emojiType;

    EmojiType(String emojiType) {
        this.emojiType = emojiType;
    }
}
