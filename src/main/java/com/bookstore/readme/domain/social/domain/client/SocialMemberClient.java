package com.bookstore.readme.domain.social.domain.client;

import com.bookstore.readme.domain.social.domain.SocialMember;
import com.bookstore.readme.domain.social.domain.SocialType;

public interface SocialMemberClient {

    SocialType supportServer();

    SocialMember fetch(String code);

}
