package com.miguel.jobnest.domain.pagination;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class PaginationTest {
    @Test
    void shouldMapItemsCorrectly() {
        final List<String> items = Collections.singletonList("Test value");

        final PaginationMetadata paginationMetadata = new PaginationMetadata(0, 10, 10, 20);
        final Pagination<String> paginated = new Pagination<>(paginationMetadata, items);

        final Pagination<String> resultWithMap = paginated.map(i -> i);

        Assertions.assertNotNull(resultWithMap);
        Assertions.assertEquals(paginated.paginationMetadata(), resultWithMap.paginationMetadata());
        Assertions.assertEquals(paginated.items(), resultWithMap.items());
    }
}
