package com.miguel.jobnest.domain.pagination;

import java.util.List;
import java.util.function.Function;

public record Pagination<T>(
        PaginationMetadata paginationMetadata,
        List<T> items
) {
    public <R> Pagination<R> map(Function<T, R> mapper) {
        final List<R> list = this.items.stream().map(mapper).toList();

        return new Pagination<>(
                paginationMetadata,
                list
        );
    }
}
