package com.bookstore.readme.domain.book.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.BookDto;
import com.bookstore.readme.domain.book.dto.BookListDto;
import com.bookstore.readme.domain.book.dto.SortType;
import com.bookstore.readme.domain.book.request.BookPageRequest;
import com.bookstore.readme.domain.book.request.BookRequest;
import com.bookstore.readme.domain.book.response.BookResponse;
import com.bookstore.readme.domain.review.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class BookDefaultService implements BookService {
    private final BookQueryService bookQueryService;

    @Override
    @Transactional
    public BookResponse bookList() {
        List<Book> books = bookQueryService.findAll();
        List<BookDto> convertBookDtos = books.stream()
                .map(book -> {
                    BookDto convertBook = BookDto.toBookDto(book);
                    List<ReviewDto> convertReview = book.getReviews().stream()
                            .map(ReviewDto::toReviewDto)
                            .toList();

                    convertBook.getReviews().addAll(convertReview);
                    return convertBook;
                })
                .toList();

        BookListDto data = BookListDto.builder()
                .total(convertBookDtos.size())
                .limit(convertBookDtos.size())
                .page(1)
                .cursorId(-1)
                .books(convertBookDtos)
                .build();

        return BookResponse.ok(data);
    }

    @Override
    @Transactional
    public BookResponse bookList(BookPageRequest request) {
        Page<Book> scroll = Page.empty();
        //커서아이디 확인
        if (request.getBookId() == null) {

            //정렬 기준 확인
            if (request.getSort() == SortType.ID) {
                //아이디
                Sort orders = request.getAscending() ? Sort.by("id").ascending() : Sort.by("id").descending();
                PageRequest pageRequest = PageRequest.of(0, request.getLimit(), orders);
                scroll = bookQueryService.scroll(request.getBookId(), request.getSort(), request.getAscending(), pageRequest);

            } else if (request.getSort() == SortType.PRICE) {
                //가격
                Sort orders = request.getAscending() ? Sort.by(Sort.Order.asc("price"), Sort.Order.asc("id")) : Sort.by(Sort.Order.desc("price"), Sort.Order.asc("id"));
                PageRequest pageRequest = PageRequest.of(0, request.getLimit(), orders);
                scroll = bookQueryService.scroll(request.getBookId(), request.getSort(), request.getAscending(), pageRequest);

            } else if (request.getSort() == SortType.POPULATION) {
                //인기
                Sort orders = request.getAscending() ? Sort.by(Sort.Order.asc("bookmarked"), Sort.Order.asc("id")) : Sort.by(Sort.Order.desc("bookmarked"), Sort.Order.asc("id"));
                PageRequest pageRequest = PageRequest.of(0, request.getLimit(), orders);
                scroll = bookQueryService.scroll(request.getBookId(), request.getSort(), request.getAscending(), pageRequest);
            }
        } else if (request.getBookId() > 0) {
            if (request.getSort() == SortType.ID) {
                //아이디
                Sort orders = request.getAscending() ? Sort.by("id").ascending() : Sort.by("id").descending();
                PageRequest pageRequest = PageRequest.of(0, request.getLimit(), orders);
                scroll = bookQueryService.scroll(request.getBookId(), request.getSort(), request.getAscending(), pageRequest);

            } else if (request.getSort() == SortType.PRICE) {
                //가격
                Sort orders = request.getAscending() ? Sort.by(Sort.Order.asc("price"), Sort.Order.asc("id")) : Sort.by(Sort.Order.desc("price"), Sort.Order.asc("id"));
                PageRequest pageRequest = PageRequest.of(0, request.getLimit(), orders);
                scroll = bookQueryService.scroll(request.getBookId(), request.getSort(), request.getAscending(), pageRequest);
            } else if (request.getSort() == SortType.POPULATION) {
                //인기
                Sort orders = request.getAscending() ? Sort.by(Sort.Order.asc("bookmarked"), Sort.Order.asc("id")) : Sort.by(Sort.Order.desc("bookmarked"), Sort.Order.asc("id"));
                PageRequest pageRequest = PageRequest.of(0, request.getLimit(), orders);
                scroll = bookQueryService.scroll(request.getBookId(), request.getSort(), request.getAscending(), pageRequest);
            }
        } else {
            // 그냥 리턴
            return BookResponse.emptyData();
        }

        List<Book> books = scroll.getContent();
        List<BookDto> convertBootDtos = books.stream()
                .map(book -> {
                    BookDto convertBook = BookDto.toBookDto(book);
                    List<ReviewDto> convertReview = book.getReviews().stream()
                            .map(ReviewDto::toReviewDto)
                            .toList();

                    convertBook.getReviews().addAll(convertReview);
                    return convertBook;
                })
                .toList();

        int nextCursorId = scroll.hasNext() ? books.get(books.size() - 1).getId().intValue() : -1;
        BookListDto data = BookListDto.builder()
                .total(bookQueryService.count().intValue())
                .limit(request.getLimit())
                .page(10000)
                .cursorId(nextCursorId)
                .books(convertBootDtos)
                .build();

        return BookResponse.ok(data);
    }

    @Override
    public BookResponse bookSave(BookRequest request) {
        Book book = request.toBook();
        bookQueryService.save(book);
        return BookResponse.ok(true);
    }

    public BookResponse bookmarkCount(Long bookId) {
        Book book = bookQueryService.findById(bookId);
        Integer bookmarked = book.getBookmarked();
        return BookResponse.ok(bookmarked);
    }
}
