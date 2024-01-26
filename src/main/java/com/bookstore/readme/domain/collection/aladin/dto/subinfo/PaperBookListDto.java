package com.bookstore.readme.domain.collection.aladin.dto.aladdin.subinfo;

import lombok.Data;

@Data
public class PaperBookListDto {
    private Integer itemId;
    private String isbn;
    private Integer priceSales;
    private String link;
}
