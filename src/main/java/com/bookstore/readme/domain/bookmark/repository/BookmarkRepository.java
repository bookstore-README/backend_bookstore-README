package com.bookstore.readme.domain.bookmark.repository;


import com.bookstore.readme.domain.bookmark.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByBookIdAndMemberId(Long bookId, Long memberId);
    List<Bookmark> findByBookId(Long bookId);
}
