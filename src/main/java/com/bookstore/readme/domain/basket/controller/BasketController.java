package com.bookstore.readme.domain.basket.controller;

import com.bookstore.readme.domain.basket.dto.BasketSearchDto;
import com.bookstore.readme.domain.basket.request.BasketDeleteRequest;
import com.bookstore.readme.domain.basket.response.BasketResponse;
import com.bookstore.readme.domain.basket.service.BasketDeleteService;
import com.bookstore.readme.domain.basket.service.BasketSaveService;
import com.bookstore.readme.domain.basket.service.BasketSearchService;
import com.bookstore.readme.domain.member.model.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basket")
@Tag(name = "장바구니 API")
public class BasketController {

    private final BasketSaveService basketSaveService;
    private final BasketSearchService basketSearchService;
    private final BasketDeleteService basketDeleteService;

    @Operation(description = "로그인 한 회원의 도서 아이디로 장바구니를 등록하는 API")
    @PostMapping("/{bookId}")
    public ResponseEntity<Object> test(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Parameter(description = "저장할 도서 아이디", required = true) @PathVariable(name = "bookId") Integer bookId
    ) {
        Long save = basketSaveService.save(bookId, memberDetails.getMemberId());
        return ResponseEntity.ok(BasketResponse.of(save));
    }

    @Operation(description = "로그인 한 회원의 등록한 장바구니 목록 가져오기 API")
    @GetMapping()
    public ResponseEntity<Object> searchBasketByMemberId(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        List<BasketSearchDto> result = basketSearchService.searchBasketByMemberId(memberDetails.getMemberId());
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
