package com.bookstore.readme.domain.collection.aladin.dto.aladdin.subinfo.used;

import lombok.Data;

@Data
public class UsedListDto {
    private UsedDto aladinUsed;
    private UserUsedDto userUsed;
    private SpaceUsedDto spaceUsed;
}
