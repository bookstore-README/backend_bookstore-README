package com.bookstore.readme.domain.bookmark.repository;


import com.bookstore.readme.domain.bookmark.domain.Bookmark;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, JpaSpecificationExecutor<Bookmark> {
    Optional<Bookmark> findByBookIdAndMemberId(Long bookId, Long memberId);

    List<Bookmark> findByBookId(Long bookId);

    List<Bookmark> findByMemberId(Long memberId);
}
