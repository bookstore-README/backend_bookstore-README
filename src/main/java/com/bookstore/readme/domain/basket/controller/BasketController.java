package com.bookstore.readme.domain.basket.controller;

import com.bookstore.readme.domain.basket.dto.BasketSearchDto;
import com.bookstore.readme.domain.basket.request.BasketDeleteRequest;
import com.bookstore.readme.domain.basket.response.BasketResponse;
import com.bookstore.readme.domain.basket.service.BasketDeleteService;
import com.bookstore.readme.domain.basket.service.BasketSaveService;
import com.bookstore.readme.domain.basket.service.BasketSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basket")
@Tag(name = "장바구니 API")
public class BasketController {

    private final BasketSaveService basketSaveService;
    private final BasketSearchService basketSearchService;
    private final BasketDeleteService basketDeleteService;

    @Operation(description = "도서 아이디와 회원 아이디로 장바구니를 등록하는 API")
    @PostMapping("/{bookId}/{memberId}")
    public ResponseEntity<Object> test(
            @Parameter(description = "저장할 도서 아이디", required = true) @PathVariable(name = "bookId") Integer bookId,
            @Parameter(description = "저장할 회원 아이디", required = true) @PathVariable(name = "memberId") Integer memberId
    ) {
        Long save = basketSaveService.save(bookId, memberId);
        return ResponseEntity.ok(BasketResponse.of(save));
    }

    @Operation(description = "회원 아이디로 등록한 장바구니 목록 가져오기 API")
    @GetMapping("/{memberId}")
    public ResponseEntity<Object> searchBasketByMemberId(
            @Parameter(description = "조회할 회원 아이디", required = true) @PathVariable(name = "memberId") Integer memberId
    ) {
        List<BasketSearchDto> result = basketSearchService.searchBasketByMemberId(memberId.longValue());
        return ResponseEntity.ok(BasketResponse.of(result));
    }

    @Operation(description = "장바구니 아이디 리스트로 삭제하는 API")
    @DeleteMapping
    public ResponseEntity<Object> deleteAllBasketByBasketId(
            @ParameterObject @Valid BasketDeleteRequest request
    ) {
        List<Integer> result = basketDeleteService.deleteAllByBasketId(request.getBasketIds());
        return ResponseEntity.ok(BasketResponse.of(result));
    }
}
