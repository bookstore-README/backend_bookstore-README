package com.bookstore.readme.common.security.config;

import com.bookstore.readme.common.jwt.*;
import com.bookstore.readme.common.security.filter.CustomAuthenticationFilter;
import com.bookstore.readme.common.security.filter.JwtAuthorizationFilter;
import com.bookstore.readme.common.security.handler.SignInFailureHandler;
import com.bookstore.readme.common.security.handler.SignInSuccessHandler;
import com.bookstore.readme.common.security.service.CustomUserDetailService;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberRepository memberRepository;
    private final JwtTokenService jwtTokenService;
    private final ObjectMapper objectMapper;
    private final CustomUserDetailService customUserDetailService;

    private final String[] permitUrl = new String[]{"/swagger", "/swagger-ui.html", "/swagger-ui/**"
            , "/api-docs", "/api-docs/**", "/v3/api-docs/**", "/uploadImage/**"
    };

    private final String[] apiPermit = new String[]{
            "/review/**", "/book/**", "/category/**", "/basket/**", "/community/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Profile(value = "dev")
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> corsFilter()))
                .csrf(AbstractHttpConfigurer::disable) // csrf 공격 옵션 X
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll(); // Option 메서드 허용
                    authorizeRequests.requestMatchers(
                            "/member", "/member/sign-in", "/social/**"
                    ).permitAll(); //Permit
                    authorizeRequests.requestMatchers(permitUrl).permitAll();
                    authorizeRequests.requestMatchers(apiPermit).permitAll();
                    authorizeRequests.anyRequest().authenticated();
                })
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        // http
        //         .addFilterBefore(getCustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        //         .addFilterAt(getCustomAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http
                .addFilterAfter(getCustomAuthenticationFilter(), LogoutFilter.class)
                .addFilterBefore(getCustomAuthorizationFilter(), CustomAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Profile(value = "default")
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {

        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagementConfigurer
                        -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests.requestMatchers("/member", "/member/sign-in", "/social/**").permitAll();
                    authorizeRequests.requestMatchers(PathRequest.toH2Console()).permitAll();
                    authorizeRequests.requestMatchers(permitUrl).permitAll();
                    authorizeRequests.anyRequest().authenticated();
                })
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        /*
         *  JWT 인증(Authentication) : 로그인 후 정상적이라면 JWT 발급하는 행동
         *  JWT 인가(Authorization) : JWT Token에 있는 정보를 이용하여 토큰 유효 확인
         */
        http
                .addFilterAfter(getCustomAuthenticationFilter(), LogoutFilter.class)
                .addFilterBefore(getCustomAuthorizationFilter(), CustomAuthenticationFilter.class);

        return http
                .build();
    }

    @Bean
    public CorsConfiguration corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000", "http://localhost:3001"
                , "https://localhost:3001", "https://front-bookstore-readme-virid.vercel.app/"
                , "https://readme-bookstore.store", "https://front-bookstore-readme.vercel.app/"
        ));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setExposedHeaders(Collections.singletonList("Set-Cookie"));
        config.setMaxAge(3600L);
        return config;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailService);
        return new ProviderManager(provider);
    }

    /**
     * 로그인 성공 시 호출되는 LoginSuccessJWTProviderHandler 빈 등록
     */
    @Bean
    public SignInSuccessHandler signInSuccessHanlder() {
        return new SignInSuccessHandler(jwtTokenService, memberRepository);
    }

    /**
     * 로그인 실패 시 호출되는 LoginFailureHandler 빈 등록
     */
    @Bean
    public SignInFailureHandler signInFailureHanlder() {
        return new SignInFailureHandler();
    }

    @Bean
    public JwtAuthorizationFilter getCustomAuthorizationFilter() throws Exception {
        return new JwtAuthorizationFilter(jwtTokenService, memberRepository);
    }

    @Bean
    public CustomAuthenticationFilter getCustomAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(objectMapper);

        customAuthenticationFilter.setAuthenticationManager(authenticationManager());
        customAuthenticationFilter.setAuthenticationSuccessHandler(signInSuccessHanlder()); // 로그인 성공 핸들러
        customAuthenticationFilter.setAuthenticationFailureHandler(signInFailureHanlder()); // 로그인 실패 핸들러

        return customAuthenticationFilter;
    }

}
