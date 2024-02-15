package com.bookstore.readme.domain.book.service.page;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.favorite.BookDto;
import com.bookstore.readme.domain.book.dto.favorite.BookPageDto;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.book.request.FavoriteCategoryRequest;
import com.bookstore.readme.domain.bookmark.dto.SortType;
import com.bookstore.readme.domain.category.domain.Category;
import com.bookstore.readme.domain.category.dto.CategoryDto;
import com.bookstore.readme.domain.category.dto.CategoryInfo;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FavoritePageService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public BookPageDto searchRandomBookPage(Long memberId, FavoriteCategoryRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, SortType.ID.getSortType());
        PageRequest pageRequest = PageRequest.of(0, request.getLimit(), sort);

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

        return BookPageDto.builder()
                .total(results.size())
                .limit(request.getLimit())
                .books(results)
                .memberCategory(Arrays.stream(member.getCategories().split(",")).toList())
                .build();
    }

    @Transactional
    public BookPageDto searchRandomBook(Long memberId) {
        Sort sort = Sort.by(Sort.Direction.DESC, SortType.ID.getSortType());
        PageRequest pageRequest = PageRequest.of(0, 4, sort);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));

        String categoryId = member.getCategories();
        if (categoryId == null || categoryId.isEmpty()) {
            //모든 카테고리중 아무거나 3개
        } else {

        }

        List<String> categories = new ArrayList<>();
        categories.add("국내도서");

        Page<Book> randomBookPage = bookRepository.findFavoriteBookPage(categories, pageRequest);
        List<Book> contents = randomBookPage.getContent();
        List<BookDto> results = contents.stream()
                .map(BookDto::of)
                .toList();

        return BookPageDto.builder()
                .total(results.size())
                .limit(100)
                .books(results)
                .memberCategory(categories)
                .build();
    }

    /**
     * @param categoryId List 로 변환할 categoryId ex) '1,2,3'
     * @param filter     변환할 List에 포함할 필터 데이터 ex) '[1, 2]'
     */
    private static List<Long> convertLongAndFilter(String categoryId, List<Integer> filter) {
        String[] split = categoryId.split(",");
        Stream<Long> longStream = Stream.of(split).map(Long::parseLong);

        if (!filter.isEmpty()) {
            List<Long> collect = filter.stream()
                    .map(Integer::longValue)
                    .toList();
            longStream = longStream.filter(collect::contains);
        }

        return longStream.toList();
    }
}
