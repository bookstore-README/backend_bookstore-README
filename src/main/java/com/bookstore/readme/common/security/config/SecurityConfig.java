package com.bookstore.readme.common.security.config;

import com.bookstore.readme.common.jwt.*;
import com.bookstore.readme.common.security.filter.JwtAuthenticationFilter;
import com.bookstore.readme.common.security.filter.JwtAuthorizationFilter;
import com.bookstore.readme.common.security.provider.CustomAuthenticationProvider;
import com.bookstore.readme.common.security.service.CustomUserDetailService;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Profile(value = "dev")
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        http
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
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http
                .addFilterBefore(getJwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(getJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Profile(value = "default")
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {

        http
                // .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                //     @Override
                //     public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                //         CorsConfiguration config = new CorsConfiguration();
                //         config.setAllowedOrigins(Collections.singletonList("*"));
                //         config.setAllowedMethods(Collections.singletonList("*"));
                //         config.setAllowCredentials(true);
                //         config.setAllowedHeaders(Collections.singletonList("*"));
                //         config.setMaxAge(3600L);
                //         return config;
                //     }
                // }))
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagementConfigurer
                        -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests.requestMatchers("/member/**").permitAll();
                    authorizeRequests.requestMatchers(PathRequest.toH2Console()).permitAll();
                    authorizeRequests.requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**").permitAll();
                    authorizeRequests.requestMatchers("/collection/**").denyAll();
                    authorizeRequests.anyRequest().authenticated();
                })
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http
                .addFilterBefore(getJwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(getJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http
                .build();
    }

    public JwtAuthorizationFilter getJwtAuthorizationFilter() throws Exception {
        return new JwtAuthorizationFilter(jwtTokenProvider);
    }

    public JwtAuthenticationFilter getJwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(null), jwtTokenProvider);

        // 로그인 인증 Filter 적용 url
        jwtAuthenticationFilter.setFilterProcessesUrl("/member/sign-in");
        // customAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());

        return jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}
