//package com.bookstore.readme.domain.book.service.page;
//
//import com.bookstore.readme.domain.book.domain.Book;
//import com.bookstore.readme.domain.book.dto.SortType;
//import com.bookstore.readme.domain.book.dto.page.BookDto;
//import com.bookstore.readme.domain.book.dto.page.BookPageDto;
//import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
//import com.bookstore.readme.domain.book.repository.BookRepository;
//import com.bookstore.readme.domain.book.repository.BookSpecification;
//import com.bookstore.readme.domain.book.util.PageSortUtils;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Primary
//@Service
//@RequiredArgsConstructor
//public class BookPageService extends BookPage {
//    private final BookRepository bookRepository;
//
//    @Transactional
//    public BookPageDto bookList(Integer cursorId, Integer limit, List<SortType> sortTypes, boolean ascending) {
//        Sort order = PageSortUtils.getSorts(sortTypes, ascending);
//        PageRequest pageRequest = PageRequest.of(0, limit + 1, order);
//
//        Page<Book> pageBooks = getPagination(cursorId, sortTypes.get(0), ascending, pageRequest);
//        List<Book> content = pageBooks.getContent();
//        List<BookDto> convertBooks = content.stream()
//                .map(BookDto::of)
//                .collect(Collectors.toList());
//
//        int nextCursorId = nextCursorId(pageBooks.hasNext(), convertBooks);
//
//        return BookPageDto.builder()
//                .total((int) bookRepository.count())
//                .limit(limit)
//                .cursorId(nextCursorId)
//                .books(convertBooks)
//                .build();
//    }
//
//    @Transactional
//    public BookPageDto bookList(Integer cursorId, Integer limit, List<SortType> sortTypes, boolean ascending, String... categories) {
//        StringBuilder fullCategory = new StringBuilder();
//        for (String category : categories) {
//            fullCategory.append(category).append(",");
//        }
//        fullCategory.deleteCharAt(fullCategory.length() - 1);
//
//        PageRequest pageRequest = PageRequest.of(0, limit + 1, PageSortUtils.getSorts(sortTypes, ascending));
//        Page<Book> pageBooks = getPagination(cursorId, sortTypes.get(0), ascending, pageRequest, fullCategory.toString());
//        List<Book> content = pageBooks.getContent();
//        List<BookDto> convertBooks = content.stream()
//                .map(BookDto::of)
//                .collect(Collectors.toList());
//
//        int nextCursorId = nextCursorId(pageBooks.hasNext(), convertBooks);
//        return BookPageDto.builder()
//                .total((int) bookRepository.count())
//                .limit(limit)
//                .cursorId(nextCursorId)
//                .books(convertBooks)
//                .build();
//    }
//
//    private Page<Book> getPagination(Integer cursorId, SortType sortType, boolean ascending, PageRequest pageRequest, String category) {
//        Page<Book> pageBooks;
//
//        if (cursorId == null) {
//            pageBooks = bookRepository.findAllByCategoriesStartingWith(category, pageRequest);
//        } else {
//            Book book = bookRepository.findById(cursorId.longValue())
//                    .orElseThrow(() -> new NotFoundBookByIdException(cursorId.longValue()));
//            Specification<Book> bookQuery = BookSpecification.singleSortPagination(book, sortType, ascending, category);
//            pageBooks = bookRepository.findAll(bookQuery, pageRequest);
//        }
//        return pageBooks;
//    }
//
//    private Page<Book> getPagination(Integer cursorId, SortType sortType, boolean ascending, PageRequest pageRequest) {
//        Page<Book> pageBooks;
//
//        if (cursorId == null) {
//            pageBooks = bookRepository.findAll(pageRequest);
//        } else {
//            Book book = bookRepository.findById(cursorId.longValue())
//                    .orElseThrow(() -> new NotFoundBookByIdException(cursorId.longValue()));
//            Specification<Book> bookQuery = BookSpecification.singleSortPagination(book, sortType, ascending);
//            pageBooks = bookRepository.findAll(bookQuery, pageRequest);
//        }
//        return pageBooks;
//    }
//
//
//    protected List<BookDto> getCdonvertBooks(List<Book> books) {
//        return books.stream()
//                .map(BookDto::of)
//                .collect(Collectors.toList());
//    }
//}
