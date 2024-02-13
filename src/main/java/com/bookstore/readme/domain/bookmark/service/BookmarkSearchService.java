package com.bookstore.readme.domain.bookmark.service;

import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.bookmark.domain.Bookmark;
import com.bookstore.readme.domain.bookmark.dto.SortType;
import com.bookstore.readme.domain.bookmark.dto.page.BookmarkInfoDto;
import com.bookstore.readme.domain.bookmark.dto.page.BookmarkPageDto;
import com.bookstore.readme.domain.bookmark.repository.BookmarkRepository;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkSearchService {
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public BookmarkPageDto searchBookmarkAndBookByMember(Long memberId, Integer offset, Integer limit, SortType sortType) {
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Order.desc(sortType.getSortType())));
        Page<Bookmark> bookmarks = bookmarkRepository.findAll(equalMemberId(memberId).and(equalMarked()), pageRequest);
        List<Bookmark> content = bookmarks.getContent();
        List<BookmarkInfoDto> list = content.stream()
                .map(bookmark -> BookmarkInfoDto.of(bookmark.getBook(), bookmark.getId()))
                .toList();

        return BookmarkPageDto.builder()
                .total(list.size())
                .limit(limit)
                .memberId(memberId.intValue())
                .cursorId(bookmarks.hasNext() ? offset + 1 : -1)
                .bookmarks(list)
                .build();
    }

    private static Specification<Bookmark> equalMarked() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("isMarked"));
    }

    private static Specification<Bookmark> equalMemberId(Long memberId) {
        return (root, query, criteriaBuilder) -> {
            Join<Bookmark, Member> memberJoin = root.join("member", JoinType.INNER);
            return criteriaBuilder.equal(memberJoin.get("id"), memberId);
        };
    }
}
