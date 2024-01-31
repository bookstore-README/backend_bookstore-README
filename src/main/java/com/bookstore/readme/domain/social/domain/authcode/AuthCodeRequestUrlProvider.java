package com.bookstore.readme.domain.social.domain.authcode;

import com.bookstore.readme.domain.social.domain.SocialType;

public interface AuthCodeRequestUrlProvider {

    SocialType supportServer();

    String provide();

}
