package com.bookstore.readme.domain.delivery.dto;

import com.bookstore.readme.domain.delivery.domain.BasicAddress;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BasicAddressDto {

    private Long basicAddressId;
    private String name;
    private String phone;
    private String address;

    public static BasicAddressDto of(BasicAddress basicAddress) {
        return BasicAddressDto.builder()
                .basicAddressId(basicAddress.getId())
                .name(basicAddress.getName())
                .phone(basicAddress.getPhone())
                .address(basicAddress.getAddress())
                .build();
    }

}
