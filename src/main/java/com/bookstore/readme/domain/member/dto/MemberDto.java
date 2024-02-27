package com.bookstore.readme.domain.member.dto;

import com.bookstore.readme.domain.delivery.dto.BasicAddressDto;
import com.bookstore.readme.domain.delivery.dto.DeliveryDto;
import com.bookstore.readme.domain.member.model.Member;
import lombok.*;

import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDto {

    private Long memberId;
    private String nickname;
    private String profileImage;
    private String email;
    private String password;

    private String name;
    private String phone;
    private String address;

    private List<DeliveryDto> deliveries;

    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .profileImage(profileImage)
                .email(email)
                .password(password)
                .build();
    }

    public static MemberDto of(Member member) {
        List<DeliveryDto> deliveries = DeliveryDto.ofs(member.getDeliveries());

        if(null != member.getBasicAddress()) {
            BasicAddressDto basic = BasicAddressDto.of(member.getBasicAddress());

            return MemberDto.builder()
                    .memberId(member.getId())
                    .nickname(member.getNickname())
                    .profileImage(member.getProfileImage())
                    .email(member.getEmail())
                    .name(basic.getName())
                    .phone(basic.getPhone())
                    .address(basic.getAddress())
                    .deliveries(deliveries)
                    .build();
        } else {
            return MemberDto.builder()
                    .memberId(member.getId())
                    .nickname(member.getNickname())
                    .profileImage(member.getProfileImage())
                    .email(member.getEmail())
                    .deliveries(deliveries)
                    .build();
        }
    }
}
