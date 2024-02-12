package com.bookstore.readme.domain.social.controller;

import com.bookstore.readme.domain.member.response.MemberResponse;
import com.bookstore.readme.domain.social.domain.SocialType;
import com.bookstore.readme.domain.social.dto.SocialLoginResponseDto;
import com.bookstore.readme.domain.social.service.SocialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "소셜 API")
@RequestMapping("/social")
public class SocialController {

    private final SocialService socialService;

    private static final String AUTHENTICATION = "Authentication";
    private static final String PREFIX_BEARER = "Bearer ";

    @SneakyThrows
    @GetMapping("/auth/{socialType}")
    @Operation(summary = "소셜 로그인 리다이렉트", description = "소셜 로그인 실행 시 로그인 창 리다이렉트")
    ResponseEntity<Void> redirectAuthCodeRequestUrl(
            @Parameter(description = "소셜타입", example = "google", required = true)
            @PathVariable SocialType socialType,
            HttpServletResponse response) {
        String redirectUrl = socialService.getAuthCodeRequestUrl(socialType);
        response.sendRedirect(redirectUrl);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/{socialType}")
    @Operation(summary = "소셜 로그인 진행", description = "소셜 로그인 진행 후 서비스")
    ResponseEntity<Void> login(
            @Parameter(description = "소셜타입", example = "google", required = true)
            @PathVariable SocialType socialType,
            @Parameter(description = "로그인 진행 후 인증 코드", example = "sampleX", required = true)
            @RequestParam("code") String code,
                                 HttpServletResponse response) throws IOException {
        SocialLoginResponseDto socialLoginResponseDto = socialService.login(socialType, code);

        Map<String, Object> mem = new HashMap<>();
        mem.put("email", socialLoginResponseDto.getEmail());
        mem.put("memberId", socialLoginResponseDto.getMemberId());
        mem.put(AUTHENTICATION, PREFIX_BEARER + socialLoginResponseDto.getAccessToken());

        ObjectMapper objectMapper = new ObjectMapper();
        Cookie cookie = new Cookie("refreshToken", socialLoginResponseDto.getRefreshToken());

        // response.setHeader(AUTHENTICATION, PREFIX_BEARER + socialLoginResponseDto.getAccessToken());
        response.addCookie(cookie);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), MemberResponse.ok(mem));

        log.info("로그인 성공. AccessToken : {}", socialLoginResponseDto.getRefreshToken());
        log.info("로그인 성공. RefreshToken : {}", socialLoginResponseDto.getAccessToken());

        return ResponseEntity.ok().build();
    }

}
