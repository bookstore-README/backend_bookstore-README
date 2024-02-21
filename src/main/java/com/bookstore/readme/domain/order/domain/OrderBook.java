package com.bookstore.readme.domain.order.domain;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "order_book")
public class OrderBook {

    @Id
    @GeneratedValue
    @Column(name = "orderBookId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    @Builder
    public OrderBook(Book book, Integer quantity, Order order) {
        this.book = book;
        this.quantity = quantity;
        this.order = order;
    }

    public void changeOrder(Order order) {
        this.order = order;
        order.getOrderBooks().add(this);
    }

}
