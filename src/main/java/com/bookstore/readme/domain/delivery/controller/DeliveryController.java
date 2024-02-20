package com.bookstore.readme.domain.delivery.controller;

import com.bookstore.readme.domain.delivery.domain.DeliveryStatus;
import com.bookstore.readme.domain.delivery.dto.DeliveryDto;
import com.bookstore.readme.domain.delivery.dto.DeliverySaveDto;
import com.bookstore.readme.domain.delivery.dto.DeliveryStatusDto;
import com.bookstore.readme.domain.delivery.response.DeliveryResponse;
import com.bookstore.readme.domain.delivery.service.DeliveryService;
import com.bookstore.readme.domain.member.model.MemberDetails;
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
        return ResponseEntity.ok(DeliveryResponse.ok(deliveryService.save(memberDetails.getMemberId(), deliverySaveDto)));
    }

    @GetMapping("")
    @Operation(summary = "회원 배송 목록 조회", description = "회원 배송 목록 조회 API")
    public ResponseEntity<DeliveryResponse> searchDeliveryByMember(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Parameter(description = "배송 상태값")
            @RequestParam DeliveryStatus deliveryStatus,
            @Parameter(description = "검색 시작일", example = "YYYY-MM-DD")
            @RequestParam String startDate,
            @Parameter(description = "검색 종료일", example = "YYYY-MM-DD")
            @RequestParam String endDate
    ) {

        List<DeliveryDto> deliveryDtos = deliveryService.searchByMemberId(
                memberDetails.getMemberId()
                , deliveryStatus
                , startDate
                , endDate
        );

        return ResponseEntity.ok(DeliveryResponse.ok(deliveryDtos));
    }

    @PutMapping("/{deliveryId}")
    @Operation(summary = "회원 배송 취소", description = "회원 배송 취소 API")
    public ResponseEntity<DeliveryResponse> cancleDelivery(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Parameter(description = "배송 아이디", required = true)
            @PathVariable Integer deliveryId
    ) {
        return ResponseEntity.ok(
                DeliveryResponse.ok(deliveryService.cancleDelivery(memberDetails.getMemberId()
                , deliveryId.longValue())));
    }

    @PutMapping("")
    @Operation(summary = "회원 배송 상태 변경", description = "회원 배송 상태 변경 API")
    public ResponseEntity<DeliveryResponse> changeDeliveryStatus(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Valid @RequestBody DeliveryStatusDto deliveryStatusDto
    ) {
        return ResponseEntity.ok(DeliveryResponse.ok(deliveryService.changeDelivery(memberDetails.getMemberId()
                , deliveryStatusDto)));
    }

    @DeleteMapping("/{deliveryId}")
    @Operation(summary = "회원 배송 삭제", description = "회원 배송 삭제 API")
    public ResponseEntity<Void> deleteDelivery(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Parameter(description = "배송 아이디", required = true)
            @PathVariable Integer deliveryId
    ) {
        Long l = deliveryService.deleteDelivery(deliveryId.longValue());

        return ResponseEntity.ok(null);
    }

}
