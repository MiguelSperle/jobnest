package com.miguel.jobnest.domain.pagination;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record SearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction,
        Map<String, String> filters
) {
    private static final List<String> NOT_PERMITTED_PARAMS = List.of(
            "page",
            "perPage",
            "terms",
            "sort",
            "direction"
    );

    public static SearchQuery newSearchQuery(
            final int page,
            final int perPage,
            final String terms,
            final String sort,
            final String direction,
            final Map<String, String> filters
    ) {
        return new SearchQuery(
                page,
                perPage,
                terms,
                sort,
                direction,
                cleanFilters(filters)
        );
    }

    public static SearchQuery newSearchQuery(
            final int page,
            final int perPage,
            final String terms,
            final String sort,
            final String direction
    ) {
        return new SearchQuery(
                page,
                perPage,
                terms,
                sort,
                direction,
                Map.of()
        );
    }

    public static SearchQuery newSearchQuery(
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return new SearchQuery(
                page,
                perPage,
                null,
                sort,
                direction,
                Map.of()
        );
    }

    public static SearchQuery newSearchQuery(
            final int page,
            final int perPage,
            final String sort,
            final String direction,
            final Map<String, String> filters
    ) {
        return new SearchQuery(
                page,
                perPage,
                null,
                sort,
                direction,
                cleanFilters(filters)
        );
    }

    private static Map<String, String> cleanFilters(final Map<String, String> filters) {
        return filters.entrySet().stream()
                .map(entry -> Map.entry(
                        entry.getKey().startsWith("filters.")
                                ? entry.getKey().substring("filters.".length())
                                : entry.getKey(),
                        entry.getValue()
                ))
                .filter(entry -> !NOT_PERMITTED_PARAMS.contains(entry.getKey()))
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1 // in case of duplicate keys, keep the first value
                ));
    }
}
