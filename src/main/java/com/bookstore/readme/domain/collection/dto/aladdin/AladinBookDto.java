package com.bookstore.readme.domain.collection.dto.aladdin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AladinBookDto {
    private String title;
    private String link;
    private String logo;
    private String pubDate;
    private int totalResults;
    private int startIndex;
    private int itemsPerPage;
    private String query;
    private String version;
    private int searchCategoryId;
    private String searchCategoryName;

    @JsonProperty(value = "item")
    private List<AladinBookItemDto> items;
}
