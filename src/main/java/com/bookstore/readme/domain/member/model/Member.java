package com.bookstore.readme.domain.member.model;

import com.bookstore.readme.domain.delivery.domain.Delivery;
import com.bookstore.readme.domain.order.domain.Order;
import com.bookstore.readme.domain.review.domain.Review;
import com.bookstore.readme.domain.social.domain.SocialId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@ToString
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "member_unique",
                        columnNames = {"email"}
                )
        }
)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "memberId")
    private Long id;

    private String name;

    private String nickname;

    private String profileImage;

    private String email;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private String refreshToken;

    @Embedded
    private SocialId socialId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Delivery> deliveries = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    private String categories;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(updatable = true)
    private LocalDateTime updateDate;

    @Builder
    public Member(Long id, String name, String nickname, String profileImage, String email, String password
            , MemberRole role, String refreshToken, SocialId socialId, String categories) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.email = email;
        this.password = password;
        this.role = role;
        this.refreshToken = refreshToken;
        this.socialId = socialId;
        this.categories = categories;
    }

    public void updateNewPassword(String password, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void updateCategories(List<Integer> categories) {
        List<String> collect = categories.stream().map(String::valueOf).toList();
        this.categories = String.join(",", collect);
    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
}
