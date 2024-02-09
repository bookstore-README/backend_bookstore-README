package com.bookstore.readme.domain.book.service.page;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.SortType;
import com.bookstore.readme.domain.book.dto.page.BookDto;
import com.bookstore.readme.domain.book.dto.page.BookPageDto;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.*;
import com.bookstore.readme.domain.category.dto.CategoryInfo;
import com.bookstore.readme.domain.category.service.CategorySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SingleSortAndCategoryPageService extends BookPage {
    private final BookRepository bookRepository;
    private final CategorySearchService categorySearchService;

    public BookPageDto mainBook(Integer cursorId, Integer limit, SortType sortType, boolean ascending, String search, Integer categoryId) {
        PageRequest pageRequest = PageRequest.of(0, limit + 1, getSort(sortType, ascending));
        String mainName = categoryId == 0 ? "국내도서" : "외국도서";
        Book book = null;
        if (cursorId != null) {
            book = bookRepository.findById(cursorId.longValue())
                    .orElseThrow(() -> new NotFoundBookByIdException(cursorId.longValue()));
        }

        Page<Book> pageBooks;
        if (book == null) {
            pageBooks = bookRepository.findAll(BookSpecification.categoryAndSearch(mainName, search), pageRequest);
        } else {
            pageBooks = bookRepository.findAll(BookPageSpecification.of(book, sortType, ascending, search, mainName), pageRequest);
        }

        List<Book> contents = pageBooks.getContent();
        List<BookDto> results = contents.stream()
                .map(BookDto::of)
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

    public BookPageDto subBook(Integer cursorId, Integer limit, SortType sortType, boolean ascending, String search, Integer categoryId) {
        PageRequest pageRequest = PageRequest.of(0, limit + 1, getSort(sortType, ascending));
        CategoryInfo categoryInfo = categorySearchService.searchCategoryInfo(categoryId);
        String categoryName = categoryInfo.getMainName() + "," + categoryInfo.getSubName();
        Book book = null;
        if (cursorId != null) {
            book = bookRepository.findById(cursorId.longValue())
                    .orElseThrow(() -> new NotFoundBookByIdException(cursorId.longValue()));
        }

        Page<Book> pageBooks;
        if (book == null) {
            pageBooks = bookRepository.findAll(BookSpecification.categoryAndSearch(categoryName, search), pageRequest);
        } else {
            pageBooks = bookRepository.findAll(BookSpecification.singleSortAndCategoryPagination(book, sortType, ascending, categoryName, search), pageRequest);
        }
        List<Book> contents = pageBooks.getContent();
        List<BookDto> results = contents.stream()
                .map(BookDto::of)
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

    /**
     * 여기는 맞춤형 도서 조회 입니다.
     * 100권을 조회할 것이고 커서 기반 페이징이 들어가야 합니다.
     * 카테고리는 여러개 입력을 할 수 있습니다.
     */
    public BookPageDto categories(Integer cursorId, Integer limit, SortType sortType, boolean ascending, List<Integer> categoryId) {
        PageRequest pageRequest = PageRequest.of(0, 100, getSort(sortType, ascending));
        List<CategoryInfo> categoryInfos = categorySearchService.categoryInfos(categoryId);
        List<String> categoryName = categoryInfos.stream()
                .map(categoryInfo -> categoryInfo.getMainName() + "," + categoryInfo.getSubName())
                .toList();

        Specification<Book> bookSpecification;
        if (cursorId == null) {
            bookSpecification = BookLikeSpecification.likeCategories(categoryName);
        } else {
            Book book = bookRepository.findById(cursorId.longValue())
                    .orElseThrow(() -> new NotFoundBookByIdException(cursorId.longValue()));
            bookSpecification = BookPageSpecification.of(book, sortType, ascending, categoryName);
        }

        Page<Book> pages = bookRepository.findAll(bookSpecification, pageRequest);
        List<Book> content = pages.getContent();
        List<BookDto> list = content.stream()
                .map(BookDto::of)
                .toList();

        return BookPageDto.builder()
                .books(list)
                .build();
    }
}
