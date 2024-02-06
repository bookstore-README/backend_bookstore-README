package com.bookstore.readme.domain.category.Controller;

import com.bookstore.readme.domain.category.request.CategoryRequest;
import com.bookstore.readme.domain.category.response.CategoryResponse;
import com.bookstore.readme.domain.category.service.CategorySaveService;
import com.bookstore.readme.domain.category.service.CategorySearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Tag(name = "카테고리 API")
public class CategoryController {
    private final CategorySaveService saveService;
    private final CategorySearchService categorySearchService;

    @GetMapping
    @Operation(summary = "카테고리 조회 기능", description = "카테고리를 목록으로 조회하는 API")
    public ResponseEntity<CategoryResponse> searchCategory() {
        return ResponseEntity.ok(CategoryResponse.of(categorySearchService.searchCategory()));
    }


    @PostMapping
    @Operation(deprecated = true, hidden = true)
    public ResponseEntity<CategoryResponse> saveCategory(@RequestBody CategoryRequest request) {
        saveService.save(request);
        return ResponseEntity.ok(CategoryResponse.of(true));
    }
}
