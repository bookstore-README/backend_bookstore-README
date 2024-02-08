package com.bookstore.readme.domain.book.controller;

import com.bookstore.readme.domain.book.request.BookPageRequest;
import com.bookstore.readme.domain.book.request.BookRequest;
import com.bookstore.readme.domain.book.response.BookResponse;
import com.bookstore.readme.domain.book.service.BookSaveService;
import com.bookstore.readme.domain.book.service.page.SingleSortAndCategoryPageService;
import com.bookstore.readme.domain.book.service.page.SingleSortPageService;
import com.bookstore.readme.domain.category.dto.CategoryInfo;
import com.bookstore.readme.domain.category.service.CategorySearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "도서 API")
public class BookPageController {

    private final SingleSortPageService singleSortPageService;
    private final SingleSortAndCategoryPageService singleSortAndCategoryPageService;
    private final BookSaveService bookSaveService;

    @GetMapping("/book")
    @Operation(summary = "도서 페이징 조회", description = "도서 페이징 조회 API")
    public ResponseEntity<BookResponse> bookPage(@ParameterObject @Valid BookPageRequest request) {
        return ResponseEntity.ok()
                .body(BookResponse.ok(singleSortPageService.pageBooks(
                        request.getBookId(),
                        request.getLimit(),
                        request.getSort().get(0),
                        request.getAscending(),
                        request.getSearch())));
    }

    @GetMapping("/book/{mainId}/main")
    @Operation(summary = "도서 페이징 조회(카테고리 - 대분류)", description = "대분류 ID로 도서 조회 API")
    public ResponseEntity<BookResponse> mainBook(
            @ParameterObject @Valid BookPageRequest request,
            @Parameter(description = "조회할 대분류 ID", required = true, example = "0이면 국내도서, 1이면 국외도서")
            @PathVariable(name = "mainId") Integer mainId
    ) {
        return ResponseEntity.ok()
                .body(BookResponse.ok(singleSortAndCategoryPageService.mainBook(
                        request.getBookId(),
                        request.getLimit(),
                        request.getSort().get(0),
                        request.getAscending(), request.getSearch(), mainId)));
    }

    @GetMapping("/book/{categoryId}/sub")
    @Operation(summary = "도서 페이징 조회(카테고리 - 대분류, 중분류)", description = "카테고리가 국내도서인 도서 조회 API")
    public ResponseEntity<BookResponse> subBook(
            @ParameterObject @Valid BookPageRequest request,
            @PathVariable(name = "categoryId") Integer categoryId
    ) {
        return ResponseEntity.ok()
                .body(BookResponse.ok(singleSortAndCategoryPageService.subBook(
                        request.getBookId(),
                        request.getLimit(),
                        request.getSort().get(0),
                        request.getAscending(), request.getSearch(), categoryId)));
    }


    @PostMapping("/book")
    @Operation(summary = "도서 저장", description = "도서를 저장하기 위한 API", deprecated = true, hidden = true)
    public ResponseEntity<BookResponse> bookSave(@RequestBody BookRequest request) {
        return ResponseEntity.ok(bookSaveService.bookSave(request));
    }
}
