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
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectionBookSearchService extends CollectionService {
    private final AladdinCollectionService collectionService;
    private final BookRepository bookRepository;

    public void save(List<Book> books) {
        List<Book> filterBooks = books.stream()
                .filter(book -> !bookRepository.existsByBookTitle(book.getBookTitle()))
                .toList();

        bookRepository.saveAll(filterBooks);
    }

    public List<BookDto> save(SaveDto saveDto) {
        List<AladdinSearchRequest> requests = search(saveDto);
        List<BookDto> getData = new ArrayList<>();
        for (AladdinSearchRequest request : requests) {
            BookDto search;
            try {
                search = collectionService.search(request);
                if (search.getItems().isEmpty())
                    continue;

                getData.add(search);
            } catch (Exception e) {
                log.error("뭔가 추가하는데 에러가 난거같어..{}", e.toString());
                continue;
            }

            List<BookItemDto> items = search.getItems();
            List<Book> books = items.stream()
                    .filter(item -> StringUtils.hasText(item.getCategoryName()) && item.getCategoryName().contains(">"))
                    .map(item -> {
                        String author = item.getAuthor();
                        String authors = author.replace(">", ",");

                        String categoryName = item.getCategoryName();
                        String[] split = categoryName.split(">");
                        StringBuilder categories = new StringBuilder();
                        for (int i = 0; i < 2; i++) {
                            categories.append(split[i]);
                            categories.append(",");
                        }

                        categories.deleteCharAt(categories.length() - 1);


                        try {

                        } catch (Exception e) {
                            log.debug(categoryName);
                            throw new RuntimeException();
                        }

                        return Book.builder()
                                .bookTitle(item.getTitle())
                                .publishedDate(item.getPubDate())
                                .bookImgUrl(item.getCover())
                                .authors(authors)
                                .description(item.getDescription())
                                .categories(categories.toString())
                                .averageRating(0D)
                                .price(item.getPriceStandard())
                                .build();
                    }).toList();

            save(books);
        }
        return getData;
    }

    private List<AladdinSearchRequest> search(SaveDto saveDto) {

        List<String> searches = saveDto.getSearches();
        Set<String> setSearches = Set.copyOf(searches);
        log.debug("count: {}", setSearches.size());
        QueryType queryType = saveDto.getQueryType();
        SearchTarget searchTarget = saveDto.getSearchTarget();
        List<Integer> starts = saveDto.getStarts();
        Integer maxResult = saveDto.getMaxResult();

        List<AladdinSearchRequest> requests = new ArrayList<>();
        setSearches.forEach(search -> {
            starts.forEach(start -> {
                AladdinSearchRequest aladdinSearchRequest = new AladdinSearchRequest(search, queryType, searchTarget, start, maxResult, null, null, null);
                requests.add(aladdinSearchRequest);
            });
        });

        return requests;
    }


}