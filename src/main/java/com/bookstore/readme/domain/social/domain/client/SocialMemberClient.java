package com.bookstore.readme.domain.social.domain.client;

import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.social.domain.SocialType;

public interface SocialMemberClient {

    SocialType supportServer();

    Member fetch(String code);

}
