package com.bookstore.readme.domain.collection.aladin.dto.subinfo.author;

import lombok.Data;

import java.util.List;

@Data
public class AuthorDto {
    private Integer authorId;
    private String authorName;

    //추가 요청시 제공
//    private String authorType;
//    private String authorTypeDesc;
//    private String authorInfo;
//    private String authorInfoLink;
}
