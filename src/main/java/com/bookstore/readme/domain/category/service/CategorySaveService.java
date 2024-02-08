package com.bookstore.readme.domain.category.service;

import com.bookstore.readme.domain.category.domain.Category;
import com.bookstore.readme.domain.category.repository.CategoryRepository;
import com.bookstore.readme.domain.category.request.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategorySaveService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public void save(CategoryRequest request) {
        Long mainId = request.getMainId();
        List<String> categories = request.getCategories();
        for (int i = 1; i <= categories.size(); i++) {
            String subName = categories.get(i - 1);
            String[] split = subName.split(",");
            Category category = Category.builder()
                    .mainId(mainId)
                    .mainName(mainId == 0 ? "국내도서" : "외국도서")
                    .subId((long) i)
                    .subName(split[0])
                    .link(split[1])
                    .build();
            categoryRepository.save(category);
        }
    }
}
