package com.bookstore.readme.domain.collection.request.product;

import lombok.Getter;

/**
 * 조회용 파라미터인 itemId 값이 어떤 타입인지 확인(가급적 ISBN 13자리 사용)
 * ISBN 기본값, 10 자리 <br>
 * ISBN13 13 자리<br>
 * ItemId 아이템 아이디로 입력<br>
 */
@Getter
public enum ItemIdType {
    ISBN("ISBN"),
    ISBN13("ISBN13"),
    ITEMID("ItemId");

    private final String itemidType;

    ItemIdType(String itemidType) {
        this.itemidType = itemidType;
    }
}
