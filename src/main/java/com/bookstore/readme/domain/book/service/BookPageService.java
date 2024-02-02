package com.bookstore.readme.domain.book.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.SortType;
import com.bookstore.readme.domain.book.dto.page.BookDto;
import com.bookstore.readme.domain.book.dto.page.BookPageDto;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.book.repository.BookSpecification;
import com.bookstore.readme.domain.book.request.BookPageRequest;
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
public class BookPageService {
    private final BookRepository bookRepository;

    @Transactional
    public BookPageDto bookList(BookPageRequest request) {
        Sort sort = getSort(request.getSort(), request.getAscending());
        PageRequest pageRequest = PageRequest.of(0, request.getLimit(), sort);
        Page<Book> scroll = scroll(request.getBookId(), request.getSort(), request.getAscending(), pageRequest);

        List<Book> books = scroll.getContent();
        List<BookDto> convertBootDto = books.stream()
                .map(BookDto::of)
                .toList();

        int nextCursorId = scroll.hasNext() ? books.get(books.size() - 1).getId().intValue() : -1;

        return BookPageDto.builder()
                .total((int) bookRepository.count())
                .limit(request.getLimit())
                .cursorId(nextCursorId)
                .books(convertBootDto)
                .build();
    }

    private Sort getSort(SortType sortType, boolean ascending) {
        Sort orders;
        if (sortType == SortType.ID) {
            orders = ascending ? Sort.by("id").ascending() : Sort.by("id").descending();
        } else if (sortType == SortType.PRICE) {
            orders = ascending ? Sort.by(Sort.Order.asc("price"), Sort.Order.asc("id")) : Sort.by(Sort.Order.desc("price"), Sort.Order.asc("id"));
        } else if (sortType == SortType.POPULATION) {
            orders = ascending ? Sort.by(Sort.Order.asc("bookmarked"), Sort.Order.asc("id")) : Sort.by(Sort.Order.desc("bookmarked"), Sort.Order.asc("id"));
        } else {
            return Sort.by("id").ascending();
        }

        return orders;
    }

    private Page<Book> scroll(Integer bookId, SortType sortType, boolean ascending, PageRequest pageRequest) {
        if (bookId == null)
            return bookRepository.findAll(pageRequest);

        Book book = bookRepository.findById(bookId.longValue())
                .orElseThrow(() -> new NotFoundBookByIdException(bookId.longValue()));

        Specification<Book> pagination = BookSpecification.pagination(sortType, ascending, book);
        return bookRepository.findAll(pagination, pageRequest);
    }
}
