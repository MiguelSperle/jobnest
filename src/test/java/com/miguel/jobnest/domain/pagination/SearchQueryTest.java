package com.miguel.jobnest.domain.pagination;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class SearchQueryTest {
    @Test
    void shouldReturnSearchQuery_whenCallNewSearchQuery() {
        final int page = 0;
        final int perPage = 10;
        final String terms = "test";
        final String sort = "createdAt";
        final String direction = "asc";
        final Map<String, String> filters = Map.of("status", "active");

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(page, perPage, terms, sort, direction, filters);
        System.out.println(searchQuery);
        Assertions.assertNotNull(searchQuery);
        Assertions.assertEquals(page, searchQuery.page());
        Assertions.assertEquals(perPage, searchQuery.perPage());
        Assertions.assertEquals(terms, searchQuery.terms());
        Assertions.assertEquals(sort, searchQuery.sort());
        Assertions.assertEquals(direction, searchQuery.direction());
        Assertions.assertEquals(filters, searchQuery.filters());
    }

    @Test
    void shouldReturnSearchQueryWithoutFilters_whenCallNewSearchQuery() {
        final int page = 0;
        final int perPage = 10;
        final String terms = "test";
        final String sort = "createdAt";
        final String direction = "asc";

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(page, perPage, terms, sort, direction);

        Assertions.assertNotNull(searchQuery);
        Assertions.assertEquals(page, searchQuery.page());
        Assertions.assertEquals(perPage, searchQuery.perPage());
        Assertions.assertEquals(terms, searchQuery.terms());
        Assertions.assertEquals(sort, searchQuery.sort());
        Assertions.assertEquals(direction, searchQuery.direction());
        Assertions.assertTrue(searchQuery.filters().isEmpty());
    }

    @Test
    void shouldReturnSearchQueryWithoutTermsAndFilters_whenCallNewSearchQuery() {
        final int page = 0;
        final int perPage = 10;
        final String sort = "createdAt";
        final String direction = "asc";

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(page, perPage, sort, direction);

        Assertions.assertNotNull(searchQuery);
        Assertions.assertEquals(page, searchQuery.page());
        Assertions.assertEquals(perPage, searchQuery.perPage());
        Assertions.assertNull(searchQuery.terms());
        Assertions.assertEquals(sort, searchQuery.sort());
        Assertions.assertEquals(direction, searchQuery.direction());
        Assertions.assertTrue(searchQuery.filters().isEmpty());
    }

    @Test
    void shouldReturnSearchQueryWithoutTerms_whenCallNewSearchQuery() {
        final int page = 0;
        final int perPage = 10;
        final String sort = "createdAt";
        final String direction = "asc";
        final Map<String, String> filters = Map.of("status", "active");

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(page, perPage, sort, direction, filters);

        Assertions.assertNotNull(searchQuery);
        Assertions.assertEquals(page, searchQuery.page());
        Assertions.assertEquals(perPage, searchQuery.perPage());
        Assertions.assertNull(searchQuery.terms());
        Assertions.assertEquals(sort, searchQuery.sort());
        Assertions.assertEquals(direction, searchQuery.direction());
        Assertions.assertEquals(filters, searchQuery.filters());
    }
}
