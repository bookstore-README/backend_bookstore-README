package com.bookstore.readme.domain.book.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.page.BookDto;
import com.bookstore.readme.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookNewService {
    private final BookRepository bookRepository;

    public List<BookDto> newBooks() {
        Sort sort = Sort.by(Sort.Order.desc("publishedDate"));
        PageRequest pageRequest = PageRequest.of(0, 15, sort);
        Page<Book> books = bookRepository.findAll(pageRequest);
        List<Book> content = books.getContent();
        return content.stream()
                .map(BookDto::of)
                .toList();
    }
}
