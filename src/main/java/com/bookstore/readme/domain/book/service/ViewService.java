package com.bookstore.readme.domain.book.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Cacheable(value = "bookViews", keyGenerator = "viewKeyGeneratorBean")
    public long addViewCount(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundBookByIdException(bookId));

        book.addViewCount();
        bookRepository.save(book);
        return book.getId();
    }
}
