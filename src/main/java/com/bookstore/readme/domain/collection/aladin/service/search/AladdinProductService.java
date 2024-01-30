package com.bookstore.readme.domain.collection.aladin.service.search;

import com.bookstore.readme.domain.collection.aladin.dto.BookDto;
import com.bookstore.readme.domain.collection.aladin.request.product.AladdinProductRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AladdinProductService extends AladdinService<AladdinProductRequest> {
    @Override
    public BookDto search(AladdinProductRequest request) {
        //UriComponentsBuilder url 설정
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance()
                .scheme("http").host("www.aladin.co.kr")
                .path("/ttb/api/ItemLookUp.aspx");

        //UriComponents에 들어갈 데이터 Map으로 변환
        Map<String, Object> query = request.getQuery(getDefaultQuery());

        UriComponents uriComponents = getUriComponents(uriBuilder, query);

        String jsonString = getUriString(uriComponents);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.readValue(jsonString, BookDto.class);
            return objectMapper.readValue(jsonString, BookDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
