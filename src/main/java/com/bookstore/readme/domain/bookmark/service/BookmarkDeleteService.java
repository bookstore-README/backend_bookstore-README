package com.bookstore.readme.domain.bookmark.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.bookmark.domain.Bookmark;
import com.bookstore.readme.domain.bookmark.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkDeleteService {
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public List<Long> deleteAllByBookmarkId(List<Integer> bookmarkIds) {
        List<Long> list = bookmarkIds.stream()
                .map(Integer::longValue)
                .toList();

        List<Bookmark> bookmarks = bookmarkRepository.findAllById(list);
        for (Bookmark bookmark : bookmarks) {
            Book book = bookmark.getBook();
            book.getBookmarks().remove(bookmark);
            book.changeBookmarkCount(book.getBookmarkCount() - 1);
        }

        bookmarkRepository.deleteAllById(list);
        return list;
    }
}
