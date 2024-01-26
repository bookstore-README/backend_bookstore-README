package com.bookstore.readme.domain.collection.aladin.dto.subinfo.newbook;

import lombok.Data;

@Data
public class NewBookListDto {
    private Integer itemId;
    private String isbn;
    private String isbn13;
    private Integer priceSales;
    private String link;
}
