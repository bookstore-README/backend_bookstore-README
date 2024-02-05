package com.bookstore.readme.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component("viewKeyGenerator")
public class ViewKeyGenerator implements KeyGenerator {

    @NonNull
    @Override
    public Object generate(@NonNull Object target, Method method, Object... params) {
        StringBuilder key = new StringBuilder(method.getName());
        for (Object param : params) {
            key.append("_").append(param.toString());
        }

        return key.toString();
    }

}
