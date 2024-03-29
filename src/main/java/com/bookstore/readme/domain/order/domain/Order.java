package com.bookstore.readme.domain.order.domain;

import com.bookstore.readme.domain.delivery.domain.Delivery;
// import com.bookstore.readme.domain.member.model.Member;
// import com.bookstore.readme.domain.order.dto.OrderDto;
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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "orderId")
    private Long id;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "member_id")
    // private Member member;

    @OneToOne(mappedBy = "order")
    private Delivery delivery;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrderBook> orderBooks = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column
    private LocalDateTime updateDate;

    @Builder
    public Order(Long id, Delivery delivery, List<OrderBook> orderBooks) {
        this.id = id;
        this.delivery = delivery;
        this.orderBooks = orderBooks;
    }

    // public void changeOrder(OrderBook orderBook) {
    //     this.getOrderBooks().add(orderBook);
    // }

}
