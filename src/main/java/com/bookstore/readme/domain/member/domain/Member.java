package com.bookstore.readme.domain.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String nickname;

    private String profileImage;

    @Column(unique = true)
    private String email;

    private String password;

    @Builder
    public Member(Long id, String name, String nickname, String profileImage, String email, String password) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.email = email;
        this.password = password;
    }
}
