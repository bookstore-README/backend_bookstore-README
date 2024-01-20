package com.bookstore.readme.domain.member.dto;

import com.bookstore.readme.domain.member.domain.Member;
import lombok.Data;

@Data
public class MemberLoginDto {

    private String email;
    private String password;

}
