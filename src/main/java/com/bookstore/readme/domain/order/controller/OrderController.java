package com.bookstore.readme.domain.order.controller;

import com.bookstore.readme.domain.member.model.MemberDetails;
import com.bookstore.readme.domain.order.domain.Order;
import com.bookstore.readme.domain.order.dto.OrderBookSaveDto;
import com.bookstore.readme.domain.order.dto.OrderDto;
import com.bookstore.readme.domain.order.response.OrderResponse;
import com.bookstore.readme.domain.order.service.OrderBookService;
import com.bookstore.readme.domain.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "주문 API")
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "주문 등록", description = "주문 등록 API")
    public ResponseEntity<OrderResponse> addOrders(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody List<OrderBookSaveDto> orderSaveDtos
    ) {
        OrderDto orderDto = orderService.save(memberDetails.getMemberId(), orderSaveDtos);

        return ResponseEntity.ok(OrderResponse.ok(orderDto.getOrderId()));
    }

    @GetMapping("/{orderId}")
    @Operation(deprecated = true, hidden = true)
    public ResponseEntity<OrderResponse> searchOrder(
        @AuthenticationPrincipal MemberDetails memberDetails,
        @PathVariable(name = "orderId") Integer orderId
    ) {
        OrderDto orderDto = orderService.findById(orderId.longValue());

        return ResponseEntity.ok(OrderResponse.ok(orderDto));
    }

}
