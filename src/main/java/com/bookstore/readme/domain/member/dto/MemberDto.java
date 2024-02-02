package com.bookstore.readme.domain.member.dto;

import com.bookstore.readme.domain.member.model.Member;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDto {

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

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .name(member.getName())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .email(member.getEmail())
                .password(member.getPassword())
                .build();
    }
}
