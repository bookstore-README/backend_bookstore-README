package com.bookstore.readme.domain.bookmark.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.bookmark.domain.Bookmark;
import com.bookstore.readme.domain.bookmark.dto.BookmarkCountDto;
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


        Bookmark bookmark = bookmarkRepository.findByBookIdAndMemberId(bookId, memberId)
                .orElseGet(() -> createBookmark(member, book, false));

        bookmark.changeMarked();


        int bookmarkCount = bookmark.getIsMarked() ? book.getBookmarkCount() + 1 : book.getBookmarkCount() - 1;
        book.changeBookmarkCount(bookmarkCount);

        bookmarkRepository.save(bookmark);

        return BookmarkDto.builder()
                .bookId(bookId)
                .memberId(memberId)
                .isMarked(bookmark.getIsMarked())
                .build();
    }

    @Transactional
    public BookmarkCountDto searchBookmarkCountByBook(Long bookId) {
        List<Bookmark> bookmarks = bookmarkRepository.findByBookId(bookId);
        List<Bookmark> filterBookmarks = bookmarks.stream()
                .filter(Bookmark::getIsMarked)
                .toList();

        return BookmarkCountDto.builder()
                .id(bookId)
                .bookmarkCount(filterBookmarks.size())
                .build();
    }

    @Transactional
    public BookmarkCountDto searchBookmarkCountByMember(Long memberId) {
        List<Bookmark> bookmarks = bookmarkRepository.findByMemberId(memberId);
        List<Bookmark> filterBookmarks = bookmarks.stream()
                .filter(Bookmark::getIsMarked)
                .toList();

        return BookmarkCountDto.builder()
                .id(memberId)
                .bookmarkCount(filterBookmarks.size())
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
