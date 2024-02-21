package com.bookstore.readme.domain.delivery.domain;

import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.order.domain.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "deliveryId")
    private Long id;

    private String name;

    private String phone;

    private String address;

    private String message;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String paymentMethod;

    private Integer paymentAmount;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column
    private LocalDateTime updateDate;

    public void changeMember(Member member) {
        this.member = member;
        member.getDeliveries().add(this);
    }

    @Builder
    public Delivery(String name, String phone, String address, String message
            , DeliveryStatus deliveryStatus, String paymentMethod, Integer paymentAmount
            , Order order, Member member) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.message = message;
        this.deliveryStatus = deliveryStatus;
        this.paymentMethod = paymentMethod;
        this.paymentAmount = paymentAmount;
        this.order = order;
        this.member = member;
    }

    public void updateDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

}
