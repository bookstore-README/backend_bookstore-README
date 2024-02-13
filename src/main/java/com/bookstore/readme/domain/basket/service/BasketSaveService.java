package com.bookstore.readme.domain.basket.service;

import com.bookstore.readme.domain.basket.domain.Basket;
import com.bookstore.readme.domain.basket.repository.BasketRepository;
import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasketSaveService {
    private final BasketRepository basketRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Integer bookId, Long memberId) {
        Book book = bookRepository.findById(bookId.longValue())
                .orElseThrow(() -> new NotFoundBookByIdException(bookId.longValue()));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));

        Basket basket = basketRepository.findByBookIdAndMemberId(bookId.longValue(), memberId)
                .orElseGet(() -> {
                    Basket newBasket = new Basket();
                    newBasket.changeBook(book);
                    newBasket.changeMember(member);
                    return newBasket;
                });

        basketRepository.save(basket);
        return basket.getId();
    }
}
