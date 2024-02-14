package com.bookstore.readme.domain.book.service.page;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.page.BookDto;
import com.bookstore.readme.domain.book.dto.page.BookPageDto;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.book.request.BookPageRequest;
import com.bookstore.readme.domain.book.request.FavoriteCategoryRequest;
import com.bookstore.readme.domain.bookmark.dto.SortType;
import com.bookstore.readme.domain.category.domain.Category;
import com.bookstore.readme.domain.category.repository.CategoryRepository;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FavoritePageService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public BookPageDto searchRandomBookPage(Long memberId, FavoriteCategoryRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, SortType.ID.getSortType());
        PageRequest pageRequest = PageRequest.of(0, 100, sort);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));

        //TODO MEMBER 에 데이터가 추가되면 설정
        List<Integer> requestId = request.getCategoryId();
        if (requestId == null) {
            //회원 에서 카테고리 아이디 가져오기 추가
        }

        List<Category> categories;
        if (requestId == null)
            categories = categoryRepository.findAll();
        else
            categories = categoryRepository.findAllByIdIn(requestId);

        List<String> list = categories.stream()
                .map(category -> {
                    return category.getMainName() + "," + category.getSubName();
                })
                .toList();

        Page<Book> randomBookPage = bookRepository.findRandomBookPage(list, pageRequest);
        List<Book> contents = randomBookPage.getContent();
        List<BookDto> results = contents.stream()
                .map(BookDto::of)
                .toList();

        return BookPageDto.builder()
                .total(results.size())
                .limit(100)
                .cursorId(-1)
                .books(results)
                .build();
    }

    @Transactional
    @Cacheable(value = "favoriteBooks", keyGenerator = "viewKeyGeneratorBean")
    public BookPageDto searchRandomBook(Long memberId) {
        Sort sort = Sort.by(Sort.Direction.DESC, SortType.ID.getSortType());
        PageRequest pageRequest = PageRequest.of(0, 100, sort);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));

        //TODO 회원 맞춤 도서로 설정하기
        //TODO MEMBER 에 데이터가 추가되면 설정
        List<String> categories = new ArrayList<>();
        categories.add("국내도서");

        Page<Book> randomBookPage = bookRepository.findRandomBookPage(categories, pageRequest);
        List<Book> contents = randomBookPage.getContent();
        List<BookDto> results = contents.stream()
                .map(BookDto::of)
                .toList();

        return BookPageDto.builder()
                .total(results.size())
                .limit(100)
                .cursorId(-1)
                .books(results)
                .build();
    }

    private List<String> getCategories(int limit, List<Category> categories) {
        Random random = new Random();

        List<String> result = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            int index = random.nextInt(0, categories.size());
            Category category = categories.get(index);
            String strCategory = category.getMainName() + "," + category.getSubName();
            result.add(strCategory);
        }

        return result;
    }
}
