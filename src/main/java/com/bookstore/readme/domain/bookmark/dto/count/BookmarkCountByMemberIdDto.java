package com.bookstore.readme.domain.bookmark.dto.count;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BookmarkCountByMemberIdDto extends BookmarkCountDto{
    private final Long memberId;
}
