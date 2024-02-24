package com.bookstore.readme.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.List;

@OpenAPIDefinition(servers = {
        @Server(url = "https://readme-bookstore.store", description = "README HTTPS DEV SERVER"),
        @Server(url = "http://15.165.141.22:8080", description = "README HTTP DEV SERVER"),
        @Server(url = "http://localhost:8080", description = "README LOCAL SERVER")
})
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi bookApi() {
        String[] paths = {
                "/bookmark/**",
                "/category/**",
                "/member/**",
                "/book/**",
                "/community/**",
                "/review/**",
                "/basket/**",
                "/delivery/**",
                "/social/**",
        };

        return GroupedOpenApi.builder()
                .group("book")
                .pathsToMatch(paths)
                .build();
    }

//    @Bean
//    public GroupedOpenApi noticeApi() {
//        String[] paths = {"/notice/**"};
//
//        return GroupedOpenApi.builder()
//                .group("notice")
//                .pathsToMatch(paths)
//                .build();
//    }

    @Bean
    public OpenAPI openAPI() {
        List<Tag> tagList = List.of(
                createTag("회원 API", "회원 관련 API"),
                createTag("찜하기 API", "찜하기 관련 API"),
                createTag("도서 API", "도서 관련 API"),
                createTag("커뮤니티 API", "커뮤니티 관련 API"),
                createTag("리뷰 API", "리뷰 관련 API"),
                createTag("카테고리 API", "카테고리 관련 API"),
                createTag("장바구니 API", "장바구니 관련 API"),
                createTag("배송 API", "배송 관련 API"),
                createTag("소셜 API", "소셜 관련 API")
        );

        String key = "Access Token (Bearer)";

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(key);

        SecurityScheme accessTokenSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        Components components = new Components()
                .addSecuritySchemes(key, accessTokenSecurityScheme);

        return new OpenAPI()
                .info(
                new Info().title("README WEB")
                        .description("README 웹 프로젝트 API 명세")
                        .version("v1.0.6"))
                .tags(tagList)
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    private Tag createTag(String name, String description) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setDescription(description);
        return tag;
    }
}