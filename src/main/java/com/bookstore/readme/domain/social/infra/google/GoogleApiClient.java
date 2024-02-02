package com.bookstore.readme.domain.social.infra.google;

import com.bookstore.readme.domain.social.dto.GoogleMemberResponseDto;
import com.bookstore.readme.domain.social.dto.GoogleTokenDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface GoogleApiClient {

    @PostExchange(url = "https://oauth2.googleapis.com/token", contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    GoogleTokenDto fetchToken(@RequestParam MultiValueMap<String, String> params);

    @GetExchange("https://www.googleapis.com/userinfo/v2/me")
    GoogleMemberResponseDto fetchMember(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String accessToken);


}
