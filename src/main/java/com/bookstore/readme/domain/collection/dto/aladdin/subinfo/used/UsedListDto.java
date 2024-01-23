package com.bookstore.readme.domain.collection.dto.aladdin.subinfo.used;

import lombok.Data;

@Data
public class UsedListDto {
    private UsedDto aladinUsed;
    private UserUsedDto userUsed;
    private SpaceUsedDto spaceUsed;
}
