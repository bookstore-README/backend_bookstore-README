package com.bookstore.readme.domain.book.service.page;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.favorite.BookDto;
import com.bookstore.readme.domain.book.dto.favorite.BookPageDto;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.book.request.FavoriteCategoryRequest;
import com.bookstore.readme.domain.bookmark.dto.SortType;
import com.bookstore.readme.domain.category.domain.Category;
import com.bookstore.readme.domain.category.domain.PreferredCategory;
import com.bookstore.readme.domain.category.dto.CategoryDto;
import com.bookstore.readme.domain.category.dto.CategoryInfo;
import com.bookstore.readme.domain.category.dto.MemberCategory;
import com.bookstore.readme.domain.category.repository.CategoryRepository;
import com.bookstore.readme.domain.category.response.CategoryResponse;
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
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FavoritePageService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public BookPageDto searchFavoriteBookPage(Long memberId, FavoriteCategoryRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, SortType.ID.getSortType());
        PageRequest pageRequest = PageRequest.of(0, 100, sort);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));

        List<Long> categoryId = convertLongAndFilter(member.getCategories(), request.getCategoryId());

        List<String> list = categoryRepository.findAllByIdIn(categoryId).stream()
                .map(category -> String.join(",", category.getMainName(), category.getSubName()))
                .toList();

        Page<Book> randomBookPage = bookRepository.findFavoriteBookPage(list, pageRequest);
        List<Book> contents = randomBookPage.getContent();
        List<BookDto> results = contents.stream()
                .map(BookDto::of)
                .toList();

        List<Long> categories = member.getCategories().stream()
                .map(c -> c.getCategory().getId())
                .toList();

        return BookPageDto.builder()
                .total(results.size())
                .limit(100)
                .books(results)
                .memberCategory(categories)
                .build();
    }

    @Transactional
    @Cacheable(value = "randomBook", keyGenerator = "viewKeyGeneratorBean")
    public BookPageDto searchRandomBookPage(Long memberId, FavoriteCategoryRequest request) {
        PageRequest pageRequest = PageRequest.of(0, 100);

        if (request.getCategoryId().isEmpty()) {
            throw new IllegalArgumentException("카테고리 아이디는 필수 입니다.");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));

        List<Long> categoryId = convertLongAndFilter(member.getCategories(), request.getCategoryId());

        List<String> list = categoryRepository.findAllByIdIn(categoryId).stream()
                .map(category -> String.join(",", category.getMainName(), category.getSubName()))
                .toList();

        Page<Book> randomBookPage = bookRepository.findFavoriteBookPage(list, pageRequest);
        List<Book> contents = randomBookPage.getContent();

        List<BookDto> results = contents.stream()
                .map(BookDto::of)
                .collect(Collectors.toList());

        Collections.shuffle(results);
        List<BookDto> randomBook = results.subList(0, 4);

        List<Long> categories = member.getCategories().stream()
                .map(c -> c.getCategory().getId())
                .toList();

        return BookPageDto.builder()
                .total(results.size())
                .limit(4)
                .books(randomBook)
                .memberCategory(categories)
                .build();
    }


    /**
     * @param categories List 로 변환할 categoryId ex) '1,2,3'
     * @param filter     변환할 List에 포함할 필터 데이터 ex) '[1, 2]'
     */
    private static List<Long> convertLongAndFilter(Set<PreferredCategory> categories, List<Integer> filter) {
        Stream<Long> longStream = categories.stream().map(c -> c.getCategory().getId());

        if (!filter.isEmpty()) {
            List<Long> collect = filter.stream()
                    .map(Integer::longValue)
                    .toList();
            longStream = longStream.filter(collect::contains);
        }

        return longStream.toList();
    }
}
