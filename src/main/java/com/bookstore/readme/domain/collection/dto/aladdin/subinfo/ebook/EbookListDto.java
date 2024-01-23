package com.bookstore.readme.domain.collection.dto.aladdin.subinfo.ebook;

import lombok.Data;

@Data
public class EbookListDto {
    private Integer itemId;
    private String isbn;
    private String isbn13;
    private Integer priceSales;
    private String link;
}
