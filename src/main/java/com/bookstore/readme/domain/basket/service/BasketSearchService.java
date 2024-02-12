package com.bookstore.readme.domain.basket.service;


import com.bookstore.readme.domain.basket.domain.Basket;
import com.bookstore.readme.domain.basket.dto.BasketSearchDto;
import com.bookstore.readme.domain.basket.repository.BasketRepository;
import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.search.BookSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketSearchService {
    private final BasketRepository basketRepository;

    @Transactional
    public List<BasketSearchDto> searchBasketByMemberId(Long memberId) {
        List<Basket> baskets = basketRepository.findAllByMemberId(memberId);
        return baskets.stream()
                .map(basket -> BasketSearchDto.of(basket.getBook()))
                .toList();
    }
}
