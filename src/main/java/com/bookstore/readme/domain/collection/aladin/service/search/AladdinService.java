package com.bookstore.readme.domain.collection.aladin.service.search;

import com.bookstore.readme.domain.collection.aladin.dto.BookDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AladdinService<T> {
    @Value("${book_api_aladdin}")
    protected String apiKey;

    public abstract BookDto search(T request);

    protected Map<String, Object> getDefaultQuery() {
        Map<String, Object> defaultMap = new HashMap<>();
        defaultMap.put("ttbkey", apiKey);
        defaultMap.put("Output", "JS");
        defaultMap.put("InputEncoding", "utf-8");
        defaultMap.put("Version", "20131101");
        return defaultMap;
    }

    /**
     * UriComponts 를 생성하기 위해 queryParam을 설정하는 메소드
     *
     * @param query                쿼리 파라미터로 추가할 데이터
     * @param uriComponentsBuilder UriComponents 를 만들 builder
     * @return 설정을 모두 마친 UriComponents
     */
    protected static UriComponents getUriComponents(UriComponentsBuilder uriComponentsBuilder, Map<String, Object> query) {
        for (Map.Entry<String, Object> entry : query.entrySet()) {
            uriComponentsBuilder.queryParamIfPresent(entry.getKey(), Optional.ofNullable(entry.getValue()));
        }

        return uriComponentsBuilder.build();
    }

    /**
     * WebClient로 알라딘 API 요청을 보내 결과 값을 가져오는 메소드
     *
     * @param uriComponent url, queryParam 설정을 마친 UriComponents
     * @return 요청으로 받아온 Json String 데이터
     */
    protected static String getUriString(UriComponents uriComponent) {
        WebClient webClient = WebClient.create();
        return webClient.get()
                .uri(uriComponent.toUri())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
