package com.bookstore.readme.domain.book.service.page;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.SortType;
import com.bookstore.readme.domain.book.dto.page.BookDto;
import com.bookstore.readme.domain.book.dto.page.BookPageDto;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.*;
import com.bookstore.readme.domain.bookmark.domain.Bookmark;
import com.bookstore.readme.domain.bookmark.dto.BookmarkDto;
import com.bookstore.readme.domain.bookmark.repository.BookmarkRepository;
import com.bookstore.readme.domain.category.dto.CategoryInfo;
import com.bookstore.readme.domain.category.service.CategorySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SingleSortAndCategoryPageService extends BookPage {
    private final BookRepository bookRepository;
    private final CategorySearchService categorySearchService;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public BookPageDto mainBook(Long memberId, Integer cursorId, Integer limit, SortType sortType, boolean ascending, String search, Integer categoryId) {
        PageRequest pageRequest = PageRequest.of(0, limit + 1, getSort(sortType, ascending));
        String mainName = categoryId == 0 ? "국내도서" : "외국도서";
        Book book = null;
        if (cursorId != 0) {
            book = bookRepository.findById(cursorId.longValue())
                    .orElseThrow(() -> new NotFoundBookByIdException(cursorId.longValue()));
        }

        Page<Book> pageBooks;
        if (book == null) {
            pageBooks = bookRepository.findAll(BookPageSpecification.of(search, mainName), pageRequest);
        } else {
            pageBooks = bookRepository.findAll(BookPageSpecification.of(book, sortType, ascending, search, mainName), pageRequest);
        }

        List<Book> contents = pageBooks.getContent();
        List<BookDto> results = contents.stream()
                .map((Book book1) -> {
                    Bookmark bookmark = bookmarkRepository.findByBookIdAndMemberId(book1.getId(), memberId)
                            .orElseGet(() -> Bookmark.builder()
                                    .isMarked(false)
                                    .build());

                    return BookDto.of(book1, BookmarkDto.builder()
                            .bookmarkId(bookmark.getId() == null ? -1 : bookmark.getId())
                            .bookId(bookmark.getBook() == null ? -1 : bookmark.getBook().getId())
                            .memberId(bookmark.getMember() == null ? -1 : bookmark.getMember().getId())
                            .isMarked(bookmark.getIsMarked())
                            .build());
                })
                .limit(limit)
                .toList();

        int nextCursorId = pageBooks.hasNext() ? contents.get(contents.size() - 1).getId().intValue() : -1;
        return BookPageDto.builder()
                .total(results.size())
                .limit(limit)
                .cursorId(nextCursorId)
                .books(results)
                .build();
    }

    @Transactional
    public BookPageDto subBook(Long memberId, Integer cursorId, Integer limit, SortType sortType, boolean ascending, String search, Integer categoryId) {
        PageRequest pageRequest = PageRequest.of(0, limit + 1, getSort(sortType, ascending));
        CategoryInfo categoryInfo = categorySearchService.searchCategoryInfo(categoryId);
        String categoryName = categoryInfo.getMainName() + "," + categoryInfo.getSubName();
        Book book = null;
        if (cursorId != 0) {
            book = bookRepository.findById(cursorId.longValue())
                    .orElseThrow(() -> new NotFoundBookByIdException(cursorId.longValue()));
        }

        Page<Book> pageBooks;
        if (book == null) {
            pageBooks = bookRepository.findAll(BookPageSpecification.of(search, categoryName), pageRequest);
        } else {
            pageBooks = bookRepository.findAll(BookPageSpecification.of(book, sortType, ascending, search, categoryName), pageRequest);
        }
        List<Book> contents = pageBooks.getContent();
        List<BookDto> results = contents.stream()
                .map((Book book1) -> {
                    Bookmark bookmark = bookmarkRepository.findByBookIdAndMemberId(book1.getId(), memberId)
                            .orElseGet(() -> Bookmark.builder()
                                    .isMarked(false)
                                    .build());

                    return BookDto.of(book1, BookmarkDto.builder()
                            .bookmarkId(bookmark.getId() == null ? -1 : bookmark.getId())
                            .bookId(bookmark.getBook() == null ? -1 : bookmark.getBook().getId())
                            .memberId(bookmark.getMember() == null ? -1 : bookmark.getMember().getId())
                            .isMarked(bookmark.getIsMarked())
                            .build());
                })
                .limit(limit)
                .toList();

        int nextCursorId = pageBooks.hasNext() ? contents.get(contents.size() - 1).getId().intValue() : -1;
        return BookPageDto.builder()
                .total(results.size())
                .limit(limit)
                .cursorId(nextCursorId)
                .books(results)
                .build();
    }

    @Transactional
    public BookPageDto categories(Integer cursorId, Integer limit, SortType sortType, boolean ascending, List<Integer> categoryId) {
        PageRequest pageRequest = PageRequest.of(0, limit, getSort(sortType, ascending));
        List<CategoryInfo> categoryInfos = categorySearchService.categoryInfos(categoryId);
        List<String> categoryName = categoryInfos.stream()
                .map(categoryInfo -> categoryInfo.getMainName() + "," + categoryInfo.getSubName())
                .toList();

        Specification<Book> bookSpecification;
        if (cursorId == 0) {
            bookSpecification = BookLikeSpecification.likeCategories(categoryName);
        } else {
            Book book = bookRepository.findById(cursorId.longValue())
                    .orElseThrow(() -> new NotFoundBookByIdException(cursorId.longValue()));
            bookSpecification = BookPageSpecification.of(book, sortType, ascending, categoryName);
        }

        Page<Book> pages = bookRepository.findAll(bookSpecification, pageRequest);
        List<Book> content = pages.getContent();
        List<BookDto> list = content.stream()
                .map((Book book) -> BookDto.of(book, null))
                .toList();

        return BookPageDto.builder()
                .books(list)
                .build();
    }
}
