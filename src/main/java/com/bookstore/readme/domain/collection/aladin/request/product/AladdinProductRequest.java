package com.bookstore.readme.domain.collection.request.product;

import com.bookstore.readme.domain.collection.request.CoverSize;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AladdinProductRequest {
    @NotEmpty(message = "Item Id는 필수 입력 값입니다.")
    private String itemId;

    @NotNull(message = "Item Id Type은 필수 입력 값입니다.")
    private ItemIdType itemIdType;

    private CoverSize cover;

//    private String outPut;
//    private String partner;
//    private String version;
//    private Integer includeKey;
//    private String offCode;

//    요청시 OptResult=ebookList,usedList,reviewList 와 같은 Array 형태로 요청
//    private OptionResultDto optResult;
}
