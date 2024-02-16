package com.bookstore.readme.domain.delivery.controller;

import com.bookstore.readme.domain.delivery.dto.DeliveryDto;
import com.bookstore.readme.domain.delivery.dto.DeliverySaveDto;
import com.bookstore.readme.domain.delivery.dto.DeliveryStatusDto;
import com.bookstore.readme.domain.delivery.response.DeliveryResponse;
import com.bookstore.readme.domain.delivery.service.DeliveryService;
import com.bookstore.readme.domain.member.model.MemberDetails;
import com.bookstore.readme.domain.order.dto.OrderBookSaveDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "배송 API")
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping("")
    @Operation(summary = "배송 등록", description = "배송 등록 API")
    public ResponseEntity<DeliveryResponse> addDelivery(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody DeliverySaveDto deliverySaveDto
    ) {

        DeliveryDto deliveryDto = deliveryService.save(memberDetails.getMemberId(), deliverySaveDto);

        return ResponseEntity.ok(DeliveryResponse.ok(deliveryDto));
    }

    @GetMapping("")
    @Operation(summary = "회원 배송 목록 조회", description = "회원 배송 목록 조회 API")
    public ResponseEntity<DeliveryResponse> searchDeliveryByMember(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        return ResponseEntity.ok(DeliveryResponse.ok(""));
    }

    @PutMapping("/{deliveryId}")
    @Operation(summary = "회원 배송 취소", description = "회원 배송 취소 API")
    public ResponseEntity<DeliveryResponse> cancleDelivery(
            @Parameter(description = "배송 아이디", required = true)
            @PathVariable Integer deleveryId
    ) {
        return ResponseEntity.ok(DeliveryResponse.ok(""));
    }

    @PutMapping("")
    @Operation(summary = "회원 배송 상태 변경", description = "회원 배송 상태 변경 API")
    public ResponseEntity<DeliveryResponse> changeDeliveryStatus(
            @Valid @RequestBody DeliveryStatusDto deliveryStatusDto
    ) {
        return ResponseEntity.ok(DeliveryResponse.ok(""));
    }


}
