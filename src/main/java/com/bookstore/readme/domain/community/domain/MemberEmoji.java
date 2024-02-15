package com.bookstore.readme.domain.community.domain;

import com.bookstore.readme.domain.community.dto.EmojiType;
import com.bookstore.readme.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEmoji {
    @Id
    @GeneratedValue
    @Column(name = "member_emoji_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    private Boolean isHeart;
    private Boolean isSmile;
    private Boolean isExcited;
    private Boolean isAngry;
    private Boolean isCrying;

    @Builder
    public MemberEmoji(Boolean isHeart, Boolean isSmile, Boolean isExcited, Boolean isAngry, Boolean isCrying) {
        this.isHeart = isHeart;
        this.isSmile = isSmile;
        this.isExcited = isExcited;
        this.isAngry = isAngry;
        this.isCrying = isCrying;
    }

    public void changeMember(Member member) {
        this.member = member;
    }

    public void changeCommunity(Community community) {
        this.community = community;
    }

    public void changeEmoji(String type, Boolean check) {
        switch (type) {
            case "heart" -> isHeart = check;
            case "smile" -> isSmile = check;
            case "excited" -> isExcited = check;
            case "angry" -> isAngry = check;
            case "crying" -> isCrying = check;
        }
    }

    public Boolean getCheck(String type) {
        return switch (type) {
            case "heart" -> isHeart;
            case "smile" -> isSmile;
            case "excited" -> isExcited;
            case "angry" -> isAngry;
            case "crying" -> isCrying;
            default -> false;
        };
    }
}
