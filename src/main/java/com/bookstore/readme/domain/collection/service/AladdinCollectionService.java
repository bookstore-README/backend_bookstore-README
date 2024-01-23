package com.bookstore.readme.domain.collection.service;

import com.bookstore.readme.domain.collection.dto.aladdin.AladinBookDto;
import com.bookstore.readme.domain.collection.request.SearchTarget;
import com.bookstore.readme.domain.collection.request.list.AladdinListRequest;
import com.bookstore.readme.domain.collection.request.list.QueryListType;
import com.bookstore.readme.domain.collection.request.list.SubSearchTarget;
import com.bookstore.readme.domain.collection.request.search.AladdinSearchRequest;
import com.bookstore.readme.domain.collection.response.AladdinResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class AladdinCollectionService implements CollectionService {
    @Value("${book_api_aladdin}")
    private String apiKey;

    public AladinBookDto list(AladdinListRequest request) throws JsonProcessingException {
        //UriComponentsBuilder url 설정
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance()
                .scheme("http").host("www.aladin.co.kr")
                .path("/ttb/api/ItemList.aspx");

        //UriComponents에 들어갈 데이터 Map으로 변환
        Map<String, Object> query = new HashMap<>();
        query.put("ttbkey", apiKey);
        query.put("QueryType", request.getQueryType().getQueryType());
        query.put("SearchTarget", request.getSearchTarget() == null ? null : request.getSearchTarget().getTarget());
        query.put("SubSearchTarget", request.getSubSearchTarget() == null ? null : request.getSubSearchTarget().getSubSearchTarget());
        query.put("Start", request.getStart());
        query.put("MaxResults", request.getMaxResults());
        query.put("Cover", request.getCover() == null ? null : request.getCover().getCoverSize());
        query.put("outofStockfilter", request.getOutofStockFilter());
        query.put("Output", "JS");
        query.put("InputEncoding", "utf-8");
        query.put("Version", "20131101");

        UriComponents uriComponents = getUriComponents(uriBuilder, query);
        log.debug("Request Url: {}", uriComponents);

        String uriString = getUriString(uriComponents);
        log.debug("Response Stirng Data: {}", uriString);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(uriString, AladinBookDto.class);
    }

    @Override
    public AladinBookDto search(AladdinSearchRequest request) throws JsonProcessingException {
        //UriComponentsBuilder url 설정
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance()
                .scheme("http").host("www.aladin.co.kr")
                .path("/ttb/api/ItemSearch.aspx");

        //UriComponents에 들어갈 데이터 Map으로 변환
        Map<String, Object> query = new HashMap<>();
        query.put("ttbkey", apiKey);
        query.put("query", request.getQuery());
        query.put("QueryType", request.getQueryType() == null ? null : request.getQueryType().getType());
        query.put("SearchTarget", request.getSearchTarget() == null ? null : request.getSearchTarget().getTarget());
        query.put("Start", request.getStart());
        query.put("Cover", request.getCover() == null ? null : request.getCover().getCoverSize());
        query.put("outofStockfilter", request.getOutofStockFilter());
        query.put("MaxResults", request.getMaxResults());
        query.put("RecentPublishFilter", 0);
        query.put("Output", "JS");
        query.put("InputEncoding", "utf-8");
        query.put("Version", "20131101");

        UriComponents uriComponent = getUriComponents(uriBuilder, query);
        String jsonString = getUriString(uriComponent);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, AladinBookDto.class);
    }

    /**
     * WebClient로 알라딘 API 요청을 보내 결과 값을 가져오는 메소드
     *
     * @param uriComponent url, queryParam 설정을 마친 UriComponents
     * @return 요청으로 받아온 Json String 데이터
     */
    private static String getUriString(UriComponents uriComponent) {
        WebClient webClient = WebClient.create();
        return webClient.get()
                .uri(uriComponent.toUri())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }


    /**
     * UriComponts 를 생성하기 위해 queryParam을 설정하는 메소드
     *
     * @param query                쿼리 파라미터로 추가할 데이터
     * @param uriComponentsBuilder UriComponents 를 만들 builder
     * @return 설정을 모두 마친 UriComponents
     */
    private static UriComponents getUriComponents(UriComponentsBuilder uriComponentsBuilder, Map<String, Object> query) {
        for (Map.Entry<String, Object> entry : query.entrySet()) {
            uriComponentsBuilder.queryParamIfPresent(entry.getKey(), Optional.ofNullable(entry.getValue()));
        }

        return uriComponentsBuilder.build();
    }
}
