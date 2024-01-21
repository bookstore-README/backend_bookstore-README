package com.bookstore.readme.common.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // csrf 공격 옵션 X
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((authorizeRequests) -> {
                    // 판매쪽 관련 로그인 여부(임시)
                    authorizeRequests.requestMatchers("/member/**").authenticated();
                    authorizeRequests.requestMatchers(PathRequest.toH2Console()).permitAll();
                    authorizeRequests.anyRequest().permitAll();
                })
                .build();
    }

}
