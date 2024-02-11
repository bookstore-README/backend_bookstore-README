package com.bookstore.readme.domain.social.infra.naver;

import com.bookstore.readme.domain.social.dto.KakaoMemberResponseDto;
import com.bookstore.readme.domain.social.dto.KakaoTokenDto;
import com.bookstore.readme.domain.social.dto.NaverMemberResponseDto;
import com.bookstore.readme.domain.social.dto.NaverTokenDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface NaverApiClient {

    @PostExchange(url = "https://nid.naver.com/oauth2.0/token")
    NaverTokenDto fetchToken(@RequestParam MultiValueMap<String, String> params);

    @GetExchange("https://openapi.naver.com/v1/nid/me")
    NaverMemberResponseDto fetchMember(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String accessToken);

}
