package com.bookstore.readme.domain.member.dto;

import com.bookstore.readme.domain.member.model.Member;
import lombok.Data;

@Data
public class MemberJoinDto {
    private String name;
    private String nickname;
    private String profileImage;
    private String email;
    private String password;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .nickname(nickname)
                .profileImage(profileImage)
                .email(email)
                .password(password)
                .build();
    }
}
