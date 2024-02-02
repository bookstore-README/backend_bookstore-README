package com.bookstore.readme.domain.bookmark.domain;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {

    @Id
    @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private boolean isMarked;

    @Builder
    public Bookmark(Member member, Book book, boolean isMarked) {
        this.member = member;
        this.book = book;
        this.isMarked = isMarked;
    }

    public void changeMarked() {
        isMarked = !isMarked;
    }
}
