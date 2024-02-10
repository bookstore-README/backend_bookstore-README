package com.bookstore.readme.domain.bookmark.service;

import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.bookmark.domain.Bookmark;
import com.bookstore.readme.domain.bookmark.dto.count.BookmarkCountByBookIdDto;
import com.bookstore.readme.domain.bookmark.dto.count.BookmarkCountByMemberIdDto;
import com.bookstore.readme.domain.bookmark.repository.BookmarkRepository;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkCountService {
    private final BookmarkRepository bookmarkRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public BookmarkCountByBookIdDto bookmarkCountByBookId(Long bookId) {
        List<Bookmark> books = bookmarkRepository.findByBookId(bookId);
        long result = books.stream()
                .map(Bookmark::getIsMarked)
                .count();


        return BookmarkCountByBookIdDto.builder()
                .bookId(bookId)
                .bookmarkCount((int) result)
                .build();
    }

    @Transactional
    public BookmarkCountByMemberIdDto bookmarkCountByMemberId(Long memberId) {
        List<Bookmark> books = bookmarkRepository.findByMemberId(memberId);
        long result = books.stream()
                .map(Bookmark::getIsMarked)
                .count();
        
        return BookmarkCountByMemberIdDto.builder()
                .memberId(memberId)
                .bookmarkCount((int) result)
                .build();
    }
}
