package com.bookstore.readme.domain.delivery.controller;

import com.bookstore.readme.domain.delivery.dto.DeliveryStatusDto;
import com.bookstore.readme.domain.delivery.response.DeliveryResponse;
import com.bookstore.readme.domain.member.model.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "배송 API")
@RequestMapping("/delivery")
public class DeliveryController {

    // @PostMapping("")
    // @Operation(summary = "배송 등록", description = "배송 등록 API")
    // public ResponseEntity<DeliveryResponse> addDelivery() {
    //
    //     return ResponseEntity.ok(DeliveryResponse.ok(""));
    // }

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
