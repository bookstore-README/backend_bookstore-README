package com.bookstore.readme.domain.category.exception;

import lombok.Getter;

@Getter
public class NotFoundCategoryByMainSubIdException extends CategoryException {
    private final Long mainId;
    private final Long subId;

    public NotFoundCategoryByMainSubIdException(Long mainId, Long subId) {
        super(CategoryStatus.NOT_FOUND_CATEGORY_BY_ID);
        this.mainId = mainId;
        this.subId = subId;
    }
}
