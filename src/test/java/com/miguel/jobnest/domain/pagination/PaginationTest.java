package com.miguel.jobnest.domain.pagination;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class PaginationTest {
    @Test
    void shouldMapItemsCorrectly() {
        final List<String> items = Collections.singletonList("Test value");
        final int currentPage = 0;
        final int perPage = 10;
        final int totalPages = 1;
        final long totalItems = 10;

        final PaginationMetadata paginationMetadata = new PaginationMetadata(currentPage, perPage, totalPages, totalItems);
        final Pagination<String> paginated = new Pagination<>(paginationMetadata, items);

        final Pagination<String> resultWithMap = paginated.map(i -> i);

        Assertions.assertNotNull(resultWithMap);
        Assertions.assertEquals(paginated.metadata(), resultWithMap.metadata());
        Assertions.assertEquals(paginated.items(), resultWithMap.items());
    }
}
