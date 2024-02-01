package com.bookstore.readme.domain.social.controller;

import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.social.domain.SocialMember;
import com.bookstore.readme.domain.social.domain.SocialType;
import com.bookstore.readme.domain.social.dto.SocialLoginResponseDto;
import com.bookstore.readme.domain.social.service.SocialService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/social")
public class SocialController {

    private final SocialService socialService;

    private static final String AUTHENTICATION = "Authentication";
    private static final String PREFIX_BEARER = "Bearer ";

    @SneakyThrows
    @GetMapping("/auth/{socialType}")
    ResponseEntity<Void> redirectAuthCodeRequestUrl(@PathVariable SocialType socialType,
                                                    HttpServletResponse response) {
        String redirectUrl = socialService.getAuthCodeRequestUrl(socialType);
        response.sendRedirect(redirectUrl);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/{socialType}")
    ResponseEntity<Void> login(@PathVariable SocialType socialType,
                               @RequestParam("code") String code,
                                 HttpServletResponse response) {
        SocialLoginResponseDto socialLoginResponseDto = socialService.login(socialType, code);

        Cookie cookie = new Cookie("refreshToken", socialLoginResponseDto.getRefreshToken());

        response.setHeader(AUTHENTICATION, PREFIX_BEARER + socialLoginResponseDto.getAccessToken());
        response.addCookie(cookie);

        log.info("로그인 성공. AccessToken : {}", socialLoginResponseDto.getRefreshToken());
        log.info("로그인 성공. RefreshToken : {}", socialLoginResponseDto.getAccessToken());

        return ResponseEntity.ok().build();
    }

}
