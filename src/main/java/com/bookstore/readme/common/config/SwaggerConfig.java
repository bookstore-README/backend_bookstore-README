package com.bookstore.readme.common.config;

import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//@OpenAPIDefinition(
//        info = @Info(title = "README WEB",
//                description = "README 웹 프로젝트 API 명세",
//                version = "v1"
//        )
//)
//@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

//    @Bean
//    public GroupedOpenApi openApi() {
//        String[] paths = {"/review/**"};
//
//        return GroupedOpenApi.builder()
//                .group("review")
//                .pathsToMatch(paths)
//                .build();
//    }

    @Bean
    public GroupedOpenApi bookApi() {
        String[] paths = {"/book/**"};

        return GroupedOpenApi.builder()
                .group("book")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        List<Tag> tagList = List.of(
                createTag("도서 API", "도서 관련 API")
        );

        return new OpenAPI().info(
                new Info().title("test")
                        .description("tset")
                        .version("v0.0.1")
        ).tags(tagList);
    }

    private Tag createTag(String name, String description) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setDescription(description);
        return tag;
    }
}