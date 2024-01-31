package com.bookstore.readme.domain.social.controller;

import com.bookstore.readme.domain.social.domain.SocialType;
import com.bookstore.readme.domain.social.service.SocialService;
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

    // @Value("${oauth2.user.google.client_id}")
    // private String client_id;
    //
    // @Value("${oauth2.user.google.client_secret}")
    // private String client_secret;
    // @Value("${oauth2.user.google.redirect_uri}")
    // private String redirect_uri;
    //
    // @Autowired
    // private JwtTokenService jwtTokenService;
    //
    // @PostMapping("/google")
    // public ResponseEntity<Object> SignInGoogle(@RequestBody OAuth2GoogleRequestDto dto) {
    //
    //     HttpHeaders header = new HttpHeaders();
    //     header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    //
    //     MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    //     params.add("grant_type", "authorization_code");
    //     params.add("client_id", client_id);
    //     params.add("client_secret", client_secret);
    //     params.add("redirect_uri", redirect_uri);
    //     // Code의 '%2F' 부분이 "/"로 인코딩이 되지 않고 발급이 되기에
    //     // errordescription: malformed-auth-code가 발생한다.
    //     params.add("code", URLDecoder.decode(dto.getAuthorizationCode(), StandardCharsets.UTF_8));
    //
    //     HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, header);
    //
    //     RestTemplate restTemplate = new RestTemplate();
    //
    //     ResponseEntity<OAuth2GoogleResponseDto> result = restTemplate
    //             .postForEntity("https://oauth2.googleapis.com/token", request, OAuth2GoogleResponseDto.class);
    //
    //     System.out.println("result.getStatusCode() = " + result.getStatusCode());
    //     System.out.println("result.getBody() = " + result.getBody());
    //
    //     String s = jwtTokenService.getBaseUrlToken(result.getBody().getId_token().split("\\.")[1]);
    //
    //     System.out.println("s = " + s);
    //
    //     return ResponseEntity.ok(result.getBody());
    // }

    private final SocialService socialService;

    @SneakyThrows
    @GetMapping("/auth/{socialType}")
    ResponseEntity<Void> redirectAuthCodeRequestUrl(@PathVariable SocialType socialType,
                                                    HttpServletResponse response) {
        String redirectUrl = socialService.getAuthCodeRequestUrl(socialType);
        response.sendRedirect(redirectUrl);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/{socialType}")
    ResponseEntity<Long> login(@PathVariable SocialType socialType,
                               @RequestParam("code") String code) {
        Long login = socialService.login(socialType, code);
        return ResponseEntity.ok(login);
    }

}
