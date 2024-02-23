package com.bookstore.readme.domain.member.dto;

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
    private String name;
    private String nickname;
    private String profileImage;
    private String email;
    private String password;
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
        List<DeliveryDto> ofs = DeliveryDto.ofs(member.getDeliveries());

        return MemberDto.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .email(member.getEmail())
                .address(member.getAddress())
                .deliveries(ofs)
                .build();
    }
}
