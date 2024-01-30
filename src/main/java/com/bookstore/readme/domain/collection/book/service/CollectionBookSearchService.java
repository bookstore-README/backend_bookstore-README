package com.bookstore.readme.domain.collection.book.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.book.service.BookQueryService;
import com.bookstore.readme.domain.collection.aladin.dto.BookDto;
import com.bookstore.readme.domain.collection.aladin.dto.BookItemDto;
import com.bookstore.readme.domain.collection.aladin.request.SearchTarget;
import com.bookstore.readme.domain.collection.aladin.request.search.AladdinSearchRequest;
import com.bookstore.readme.domain.collection.aladin.request.search.QueryType;
import com.bookstore.readme.domain.collection.aladin.service.AladdinCollectionService;
import com.bookstore.readme.domain.collection.book.request.SaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectionBookSearchService extends CollectionService {
    private final AladdinCollectionService collectionService;
    private final BookRepository bookRepository;

    @Transactional
    public List<BookDto> save(SaveDto saveDto) {
        List<AladdinSearchRequest> requests = search(saveDto);
        List<BookDto> getData = new ArrayList<>();
        for (AladdinSearchRequest request : requests) {
            BookDto search;
            try {
                search = collectionService.search(request);
                getData.add(search);
            } catch (Exception e) {
                continue;
            }

            List<BookItemDto> items = search.getItems();
            List<Book> books = items.stream()
                    .filter(item -> StringUtils.hasText(item.getCategoryName()))
                    .map(item -> {
                        log.debug("{}", item.getCategoryName());
                        String author = item.getAuthor();
                        String authors = author.replace(">", ",");

                        String categoryName = item.getCategoryName();
                        String categories = categoryName.replaceAll(">", ",");
                        log.debug("{}", categories);
                        log.debug("{}", categories.indexOf(",", 5));
                        String substring = categories.substring(0, categories.indexOf(",", 5));

                        return Book.builder()
                                .bookTitle(item.getTitle())
                                .publishedDate(item.getPubDate())
                                .bookImgUrl(item.getCover())
                                .authors(authors)
                                .description(item.getDescription())
                                .categories(substring)
                                .bookmarked(0)
                                .averageRating(0D)
                                .price(item.getPriceStandard())
                                .build();
                    }).toList();
            bookRepository.saveAll(books);
        }
        return getData;
    }

    private List<AladdinSearchRequest> search(SaveDto saveDto) {

        List<String> searches = saveDto.getSearches();
        QueryType queryType = saveDto.getQueryType();
        SearchTarget searchTarget = saveDto.getSearchTarget();
        List<Integer> starts = saveDto.getStarts();
        Integer maxResult = saveDto.getMaxResult();

        List<AladdinSearchRequest> requests = new ArrayList<>();
        searches.forEach(search -> {
            starts.forEach(start -> {
                AladdinSearchRequest aladdinSearchRequest = new AladdinSearchRequest(search, queryType, searchTarget, start, maxResult, null, null, null);
                requests.add(aladdinSearchRequest);
            });
        });

        return requests;
    }


}
