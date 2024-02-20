package com.bookstore.readme.domain.order.dto;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.order.domain.OrderBook;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderBookSaveDto {

    @Schema(description = "책 고유 ID")
    @NotNull(message = "책 고유 ID는 필수입니다.")
    Integer bookId;

    @Schema(description = "주문 책 권수")
    @NotNull(message = "주문한 책 권수는 필수입니다.")
    Integer quantity;

}
