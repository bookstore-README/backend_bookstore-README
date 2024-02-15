package com.bookstore.readme.domain.category.service;

import com.bookstore.readme.domain.category.domain.Category;
import com.bookstore.readme.domain.category.dto.CategoryDto;
import com.bookstore.readme.domain.category.dto.CategoryInfo;
import com.bookstore.readme.domain.category.dto.MemberCategory;
import com.bookstore.readme.domain.category.exception.NotFoundCategoryByIdException;
import com.bookstore.readme.domain.category.exception.NotFoundCategoryByMainSubIdException;
import com.bookstore.readme.domain.category.repository.CategoryRepository;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CategorySearchService {
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    public CategoryDto searchCategory() {
        List<Category> categories = categoryRepository.findAll();

        //국내도서
        List<CategoryInfo> domestic = categories.stream()
                .filter(category -> category.getMainId() == 0)
                .map(CategoryInfo::of)
                .toList();

        List<CategoryInfo> foreign = categories.stream()
                .filter(category -> category.getMainId() == 1)
                .map(CategoryInfo::of)
                .toList();

        return CategoryDto.builder()
                .domestic(domestic)
                .foreign(foreign)
                .build();
    }

    public CategoryInfo searchCategoryInfo(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId.longValue())
                .orElseThrow(() -> new NotFoundCategoryByIdException(categoryId.longValue()));

        return CategoryInfo.of(category);
    }

    public CategoryInfo searchCategoryInfo(Integer mainId, Integer subId) {
        Category category = categoryRepository.findByMainIdAndSubId(mainId.longValue(), subId.longValue())
                .orElseThrow(() -> new NotFoundCategoryByMainSubIdException(mainId.longValue(), subId.longValue()));

        return CategoryInfo.of(category);
    }

    public List<CategoryInfo> categoryInfos(List<Integer> categories) {
        List<Long> collect = categories.stream()
                .map(Integer::longValue)
                .collect(Collectors.toList());

        List<Category> categoryList = categoryRepository.findAllByIdIn(collect);

        return categoryList.stream()
                .map(CategoryInfo::of)
                .toList();
    }

    @Transactional
    public MemberCategory memberCategories(Long memberId) {
        //회원이 가진 데이터
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));

        String categories = member.getCategories();
        String[] split = categories.split(",");
        List<Long> list = Stream.of(split)
                .map(Long::parseLong)
                .toList();

        List<Category> result = categoryRepository.findAllByIdIn(list);
        List<CategoryInfo> list1 = result.stream()
                .map(CategoryInfo::of)
                .toList();

        return MemberCategory.builder()
                .memberCategory(list1)
                .build();
    }
}
