package com.bookstore.readme.domain.bookmark.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.bookmark.domain.Bookmark;
import com.bookstore.readme.domain.bookmark.dto.BookmarkDto;
import com.bookstore.readme.domain.bookmark.repository.BookmarkRepository;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public BookmarkDto addBookmark(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundBookByIdException(bookId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("아이디에 해당하는 회원이 없습니다."));


        Bookmark bookmark = bookmarkRepository.findByBookIdAndMemberId(bookId, memberId)
                .orElseGet(() -> createBookmark(member, book, false));

        bookmark.changeMarked();
        bookmarkRepository.save(bookmark);

        return BookmarkDto.builder()
                .bookId(bookId)
                .memberId(memberId)
                .isMarked(bookmark.isMarked())
                .build();
    }

    private Bookmark createBookmark(Member member, Book book, boolean isMarked) {
        return Bookmark.builder()
                .member(member)
                .book(book)
                .isMarked(isMarked)
                .build();
    }
}
