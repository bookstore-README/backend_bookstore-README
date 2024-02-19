package com.bookstore.readme.domain.category.domain;

import com.bookstore.readme.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PreferredCategory {

    @Id
    @GeneratedValue
    @Column(name = "preferred_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void changeMember(Member member) {
        this.member = member;
        member.getCategories().add(this);
    }

    @Builder
    public PreferredCategory(Member member, Category category) {
        this.member = member;
        this.category = category;
    }
}
