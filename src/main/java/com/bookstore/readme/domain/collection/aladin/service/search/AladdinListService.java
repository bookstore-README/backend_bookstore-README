package com.bookstore.readme.domain.collection.aladin.service.search;

import com.bookstore.readme.domain.collection.aladin.dto.BookDto;
import com.bookstore.readme.domain.collection.aladin.request.list.AladdinListRequest;
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
public class AladdinListService extends AladdinService<AladdinListRequest> {
    public BookDto search(AladdinListRequest request) {
        //UriComponentsBuilder url 설정
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance()
                .scheme("http").host("www.aladin.co.kr")
                .path("/ttb/api/ItemList.aspx");

        //UriComponents에 들어갈 데이터 Map으로 변환
        Map<String, Object> query = request.getQuery(getDefaultQuery());

        UriComponents uriComponents = getUriComponents(uriBuilder, query);

        String uriString = getUriString(uriComponents);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.readValue(uriString, BookDto.class);
            return objectMapper.readValue(uriString, BookDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
