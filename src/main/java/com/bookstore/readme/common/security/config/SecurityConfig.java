package com.bookstore.readme.common.security.config;

import com.bookstore.readme.common.jwt.JwtAuthenticationProvider;
import com.bookstore.readme.common.security.filter.CustomAuthenticationFilter;
import com.bookstore.readme.common.security.service.CustomUserDetailService;
import com.bookstore.readme.common.jwt.JwtTokenProvider;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Resource(name = "jwtTokenProvider")
    private final JwtTokenProvider jwtTokenProvider;

    private final CustomUserDetailService customUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Profile(value = "dev")
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        return http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("*"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                // csrf 공격 옵션 X
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests.requestMatchers("/member/**").permitAll();
                    authorizeRequests.requestMatchers("/collection/**").denyAll();
                    authorizeRequests.anyRequest().authenticated();
                })
                .addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .build();
    }

    @Bean
    @Profile(value = "default")
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
        return http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("*"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                // csrf 공격 옵션 X
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests.requestMatchers("/member/**").permitAll();
                    authorizeRequests.requestMatchers(PathRequest.toH2Console()).permitAll();
//                    authorizeRequests.requestMatchers("/collection/**").denyAll();
//                    authorizeRequests.anyRequest().authenticated();
                    authorizeRequests.anyRequest().permitAll();
                })
                .addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
        sharedObject.authenticationProvider(authenticationProvider());
        return sharedObject.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new JwtAuthenticationProvider(customUserDetailService, passwordEncoder());
    }


    public AbstractAuthenticationProcessingFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager(null), jwtTokenProvider);

        // Filter 적용 url
        customAuthenticationFilter.setFilterProcessesUrl("/member/sign-in");
        // customAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());

        return customAuthenticationFilter;
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return null;
        // return JwtFailureHandler();
    }
}
