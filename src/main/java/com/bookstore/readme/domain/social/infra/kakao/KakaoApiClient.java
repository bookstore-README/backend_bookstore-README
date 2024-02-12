package com.bookstore.readme.domain.social.infra.kakao;

import com.bookstore.readme.domain.social.dto.GoogleMemberResponseDto;
import com.bookstore.readme.domain.social.dto.GoogleTokenDto;
import com.bookstore.readme.domain.social.dto.KakaoMemberResponseDto;
import com.bookstore.readme.domain.social.dto.KakaoTokenDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface KakaoApiClient {

    @PostExchange(url = "https://kauth.kakao.com/oauth/token", contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoTokenDto fetchToken(@RequestParam MultiValueMap<String, String> params);

    @GetExchange("https://kapi.kakao.com/v2/user/me")
    KakaoMemberResponseDto fetchMember(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String accessToken);

}
