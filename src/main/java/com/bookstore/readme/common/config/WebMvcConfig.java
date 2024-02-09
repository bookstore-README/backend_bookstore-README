package com.bookstore.readme.common.config;

import com.bookstore.readme.domain.social.presentation.SocialTypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String filePath;

    @Value("${upload.handler}")
    private String fileHandler;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileHandler)
                .addResourceLocations(filePath);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new SocialTypeConverter());
    }
}
