package org.example.ticketing.api.dto.common;

public record Response<T> (
    String code,
    T result
) {
    public static <T> Response<T> error(String errorCode) {
        return new Response<>(errorCode, null);
    }

    public static <T> Response<T> success(T result) {
        return new Response<>("SUCCESS", result);
    }
}
