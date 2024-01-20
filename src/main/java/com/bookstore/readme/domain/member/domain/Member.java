package com.bookstore.readme.domain.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Builder
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String nickname;
    private String profileImage;
    private String email;
    private String password;
}
