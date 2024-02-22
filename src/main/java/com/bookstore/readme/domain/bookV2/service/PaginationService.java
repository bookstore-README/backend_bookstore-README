package com.bookstore.readme.domain.bookV2.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.bookV2.dto.BookInfo;
import com.bookstore.readme.domain.bookV2.dto.Pagination;
import com.bookstore.readme.domain.bookV2.request.BookPageRequest;
import com.bookstore.readme.domain.bookmark.domain.Bookmark;
import com.bookstore.readme.domain.bookmark.dto.BookmarkDto;
import com.bookstore.readme.domain.bookmark.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaginationService extends BookPagination<Pagination> {
    private final BookRepository bookRepository;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public Pagination bookPage(Long memberId, BookPageRequest request) {
        Sort sort = getSort(request.getSortType(), request.isAscending());
        PageRequest pageRequest = PageRequest.of(request.getOffset(), request.getLimit(), sort);
        Specification<Book> specification = likeAuthorsAndBookTitle(request.getSearch());
        Page<Book> books = bookRepository.findAll(specification, pageRequest);
        List<Book> content = books.getContent();
        List<BookInfo> result = content.stream()
                .map(book -> {
                    Bookmark bookmark = getBookmark(book.getId(), memberId);
                    BookmarkDto bookmarkDto = convertBookmark(bookmark);
                    return convertBook(book, bookmarkDto);
                }).toList();

        long total = StringUtils.hasText(request.getSearch()) ? bookRepository.countAllBySearch("%" + request.getSearch() + "%") : bookRepository.count();
        return Pagination.builder()
                .total((int) total)
                .limit(books.getSize())
                .offset(books.getNumber())
                .books(result)
                .build();
    }

    protected Bookmark getBookmark(Long bookId, Long memberId) {
        return bookmarkRepository.findByBookIdAndMemberId(bookId, memberId)
                .orElseGet(() -> Bookmark.builder()
                        .isMarked(false)
                        .build());
    }

}
