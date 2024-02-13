package com.bookstore.readme.domain.community.domain;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Community {
    @Id
    @GeneratedValue
    @Column(name = "community_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(updatable = true)
    private LocalDateTime updateDate;

    @Builder
    public Community(String content) {
        this.content = content;
    }

    public void changeBook(Book book) {
        this.book = book;
    }

    public void changeMember(Member member) {
        this.member = member;
    }


    public void changeContent(String content) {
        this.content = content;
    }
}
