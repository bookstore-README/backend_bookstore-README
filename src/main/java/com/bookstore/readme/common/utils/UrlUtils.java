package com.bookstore.readme.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class UrlUtils {

    /**
     * 스프링 부트 애플리케이션의 BASE URL UTIL
     * @param request the HttpServletRequest object
     * @return the base URL
     * */
    public static String getBaseUrl(HttpServletRequest request) {
        return ServletUriComponentsBuilder
                .fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
    }

}
