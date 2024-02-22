package com.bookstore.readme.domain.bookV2.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.repository.BookEqualSpecification;
import com.bookstore.readme.domain.book.repository.BookOrderSpecification;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.bookV2.dto.BookInfo;
import com.bookstore.readme.domain.bookV2.dto.InfinityScroll;
import com.bookstore.readme.domain.bookV2.request.BookPageRequest;
import com.bookstore.readme.domain.bookV2.request.SortType;
import com.bookstore.readme.domain.bookmark.domain.Bookmark;
import com.bookstore.readme.domain.bookmark.dto.BookmarkDto;
import com.bookstore.readme.domain.bookmark.repository.BookmarkRepository;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
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
public class InfinityScrollService extends BookPagination<InfinityScroll> {
    private final BookRepository bookRepository;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public InfinityScroll bookPage(Long memberId, BookPageRequest request) {
        Sort sort = getSort(request.getSortType(), request.isAscending());
        PageRequest pageRequest = PageRequest.of(0, request.getLimit() + 1, sort);
        Specification<Book> specification = likeAuthorsAndBookTitle(request.getSearch());

        if (request.getCursorId() != -1) {
            Book book = bookRepository.findById(request.getCursorId().longValue()).orElseThrow(() -> new NotFoundMemberByIdException(request.getCursorId().longValue()));
            specification = specification.and(of(book, request.getSortType(), request.isAscending()));
        }

        Page<Book> books = bookRepository.findAll(specification, pageRequest);
        List<Book> content = books.getContent();
        List<BookInfo> result = content.stream()
                .map(book -> {
                    Bookmark bookmark = getBookmark(book.getId(), memberId);
                    BookmarkDto bookmarkDto = convertBookmark(bookmark);
                    return convertBook(book, bookmarkDto);
                }).limit(request.getLimit()).toList();

        long total = StringUtils.hasText(request.getSearch()) ? bookRepository.countAllBySearch("%" + request.getSearch() + "%") : bookRepository.count();
        int nextCursorId = books.hasNext() || content.size() > request.getLimit() ? content.get(content.size() - 1).getId().intValue() : -1;
        return InfinityScroll.builder()
                .total((int) total)
                .limit(books.getSize())
                .cursorId(nextCursorId)
                .books(result)
                .build();
    }

    protected Bookmark getBookmark(Long bookId, Long memberId) {
        return bookmarkRepository.findByBookIdAndMemberId(bookId, memberId)
                .orElseGet(() -> Bookmark.builder()
                        .isMarked(false)
                        .build());
    }

    private static Specification<Book> of(Book book, SortType sortType, boolean ascending) {
        if (sortType == SortType.STAR) {
            return BookOrderSpecification.ratingCursor(book.getId(), book.getAverageRating(), ascending);
        } else if (sortType == SortType.REVIEW) {
            return BookOrderSpecification.reviewCursor(book.getId(), book.getReviewCount(), ascending);
        } else if (sortType == SortType.VIEW) {
            return BookOrderSpecification.viewCursor(book.getId(), book.getViewCount(), ascending);
        } else if (sortType == SortType.PRICE) {
            return BookOrderSpecification.priceCursor(book.getId(), book.getPrice(), ascending);
        } else if (sortType == SortType.NEWEST) {
            return BookOrderSpecification.newestCursor(book.getId(), book.getPublishedDate(), ascending)
                    .or(BookEqualSpecification.equalId(book.getId()));
        } else if (sortType == SortType.BESTSELLER) {
            return BookOrderSpecification.quantityCursor(book.getId(), book.getQuantityCount(), ascending);
        } else {
            return BookOrderSpecification.idCursor(book.getId(), ascending);
        }
    }
}
