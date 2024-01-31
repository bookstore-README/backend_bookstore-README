package com.bookstore.readme.domain.social.presentation;

import com.bookstore.readme.domain.social.domain.SocialType;
import org.springframework.core.convert.converter.Converter;

public class SocialTypeConverter implements Converter<String, SocialType> {

    @Override
    public SocialType convert(String source) {
        return SocialType.fromName(source);
    }
}
