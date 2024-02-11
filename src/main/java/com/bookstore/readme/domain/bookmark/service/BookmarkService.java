package com.bookstore.readme.domain.bookmark.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.bookmark.domain.Bookmark;
import com.bookstore.readme.domain.bookmark.dto.BookmarkAndBookDto;
import com.bookstore.readme.domain.bookmark.dto.BookmarkCountDto;
import com.bookstore.readme.domain.bookmark.dto.BookmarkDetailDto;
import com.bookstore.readme.domain.bookmark.dto.BookmarkDto;
import com.bookstore.readme.domain.bookmark.repository.BookmarkRepository;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));

        //북마크 True False 설정
        Bookmark bookmark = bookmarkRepository.findByBookIdAndMemberId(bookId, memberId)
                .orElseGet(() -> createBookmark(member, book, false));
        bookmark.changeMarked();

        //북마크 개수 설정
        int bookmarkCount = bookmark.getIsMarked() ? book.getBookmarkCount() + 1 : book.getBookmarkCount() - 1;
        book.changeBookmarkCount(bookmarkCount);
        bookmarkRepository.save(bookmark);

        return BookmarkDto.builder()
                .bookmarkId(bookmark.getId())
                .bookId(bookId)
                .memberId(memberId)
                .isMarked(bookmark.getIsMarked())
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
