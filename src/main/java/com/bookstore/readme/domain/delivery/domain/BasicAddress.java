package com.bookstore.readme.domain.delivery.domain;

import com.bookstore.readme.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BasicAddress {

    @Id
    @GeneratedValue
    @Column(name = "basicDeliveryId")
    private Long id;

    private String name;

    private String phone;

    private String address;

    @OneToOne(mappedBy = "basicAddress")
    private Member member;

    @Builder
    public BasicAddress(String name, String phone, String address, Member member) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.member = member;
    }

}
