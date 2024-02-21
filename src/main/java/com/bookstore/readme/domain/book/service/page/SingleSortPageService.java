package com.bookstore.readme.domain.book.service.page;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.SortType;
import com.bookstore.readme.domain.book.dto.page.BookDto;
import com.bookstore.readme.domain.book.dto.page.BookPageDto;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookPageSpecification;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.bookmark.domain.Bookmark;
import com.bookstore.readme.domain.bookmark.dto.BookmarkDto;
import com.bookstore.readme.domain.bookmark.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SingleSortPageService extends BookPage {
    private final BookRepository bookRepository;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public BookPageDto pageBooks(Long memberId, Integer cursorId, Integer limit, SortType sortType, boolean ascending, String search) {
        PageRequest pageRequest = PageRequest.of(0, limit + 1, getSort(sortType, ascending));
        Page<Book> pages;

        if (cursorId == 0) {
            Specification<Book> specification = BookPageSpecification.of(search);
            if (specification == null)
                pages = bookRepository.findAll(pageRequest);
            else
                pages = bookRepository.findAll(specification, pageRequest);
        } else {
            Book book = bookRepository.findById(cursorId.longValue())
                    .orElseThrow(() -> new NotFoundBookByIdException(cursorId.longValue()));

            pages = bookRepository.findAll(BookPageSpecification.of(book, sortType, ascending, search), pageRequest);
        }

        List<Book> contents = pages.getContent();
        List<BookDto> results = contents.stream()
                .map((Book book) -> {
                    Bookmark bookmark = bookmarkRepository.findByBookIdAndMemberId(book.getId(), memberId)
                            .orElseGet(() -> Bookmark.builder()
                                    .isMarked(false)
                                    .build());

                    return BookDto.of(book, BookmarkDto.builder()
                            .bookmarkId(bookmark.getId() == null ? -1 : bookmark.getId())
                            .bookId(bookmark.getBook() == null ? -1 : bookmark.getBook().getId())
                            .memberId(bookmark.getMember() == null ? -1 : bookmark.getMember().getId())
                            .isMarked(bookmark.getIsMarked())
                            .build());
                })
                .limit(limit)
                .toList();

        Long total = 0L;
        if (StringUtils.hasText(search)) {
            total = bookRepository.countAllBySearch("%" + search + "%");
        } else {
            total = bookRepository.count();
        }

        
        int nextCursorId = pages.hasNext() || contents.size() > limit ? contents.get(contents.size() - 1).getId().intValue() : -1;

        return BookPageDto.builder()
                .total(Math.toIntExact(total))
                .limit(limit)
                .cursorId(nextCursorId)
                .books(results)
                .build();
    }
}
