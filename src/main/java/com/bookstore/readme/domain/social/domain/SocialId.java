package com.bookstore.readme.domain.social.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialId {

    @Column(nullable = false, name = "social_server_id")
    private String socialServerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "social_server")
    private SocialType socialType;

    public String socialServerId() {
        return socialServerId;
    }

    public SocialType socialServer() {
        return socialType;
    }

}
