package com.bookstore.readme.domain.social.domain;

import static java.util.Locale.ENGLISH;

public enum SocialType {
    GOOGLE,
    KAKAO,
    NAVER;

    public static SocialType fromName(String type) {
        return SocialType.valueOf(type.toUpperCase(ENGLISH));
    }
}
