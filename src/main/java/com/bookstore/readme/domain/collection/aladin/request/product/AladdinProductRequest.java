package com.bookstore.readme.domain.collection.aladin.request.product;

import com.bookstore.readme.domain.collection.aladin.request.CoverSize;
import com.bookstore.readme.domain.collection.aladin.request.SearchTarget;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class AladdinProductRequest {
    @NotEmpty(message = "Item Id는 필수 입력 값입니다.")
    private String itemId;

    @NotNull(message = "Item Id Type은 필수 입력 값입니다.")
    private ItemIdType itemIdType;

    private CoverSize cover;

    private static final List<String> opts = List.of(
            "packing",
            "cardReviewImgList",
            "ratingInfo",
            "bestSellerRank",
            "previewImgList",
            "eventList",
            "authors",
            "reviewList",
            "fulldescription",
            "fulldescription2",
            "Toc",
            "Story",
            "categoryIdList",
            "mdrecommend",
            "phraseList"
    );

    public Map<String, Object> getQuery(Map<String, Object> defaultQuery) {
        defaultQuery.put("ItemId", itemId);
        defaultQuery.put("ItemIdType", itemIdType.getItemidType());
        defaultQuery.put("Cover", cover == null ? null : cover.getCoverSize());
        defaultQuery.put("OptResult", createOpt());
        return defaultQuery;
    }

    private String createOpt() {
        StringBuilder sb = new StringBuilder();
        for (String opt : opts) {
            sb.append(opt);
            sb.append(",");
        }

        int length = sb.length();
        sb.deleteCharAt(length - 1);
        return sb.toString();
    }
}
