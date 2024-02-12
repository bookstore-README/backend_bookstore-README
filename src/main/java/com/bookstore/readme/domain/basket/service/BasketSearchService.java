package com.bookstore.readme.domain.basket.service;


import com.bookstore.readme.domain.basket.domain.Basket;
import com.bookstore.readme.domain.basket.dto.BasketSearchDto;
import com.bookstore.readme.domain.basket.repository.BasketRepository;
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
                .map(basket -> BasketSearchDto.of(basket.getBook(), basket.getId()))
                .toList();
    }
}
