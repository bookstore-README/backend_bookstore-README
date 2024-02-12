package com.bookstore.readme.domain.basket.service;

import com.bookstore.readme.domain.basket.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketDeleteService {
    private final BasketRepository basketRepository;

    @Transactional
    public List<Integer> deleteAllByBasketId(List<Integer> basketIds) {
        List<Long> ids = basketIds.stream()
                .map(Integer::longValue)
                .toList();

        basketRepository.deleteAllById(ids);
        return basketIds;
    }
}
