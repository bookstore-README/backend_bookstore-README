package com.bookstore.readme.domain.social.dto;

import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.social.domain.SocialId;
import com.bookstore.readme.domain.social.domain.SocialMember;
import com.bookstore.readme.domain.social.domain.SocialType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public record GoogleMemberResponseDto(
        String id,
        String email,
        boolean verified_email,
        String name,
        String given_name,
        String family_name,
        String picture,
        String locale
) {

    public Member toDomain() {
        return Member.builder()
                .socialId(new SocialId(id, SocialType.GOOGLE))
                .email(email)
                .name(name)
                .profileImage(picture)
                .build();
    }
}
