package com.miguel.jobnest.infrastructure.configurations;

import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.infrastructure.exceptions.*;
import com.miguel.jobnest.infrastructure.utils.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        final List<ApiError.Error> errors = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> new ApiError.Error(
                fieldError.getField(), fieldError.getDefaultMessage()
        )).toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError.from(
                "Validation error", HttpStatus.BAD_REQUEST.value(), errors, TimeUtils.now()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(final Exception ex) {
        log.error("Handling unexpected exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiError.from(
                "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value(), TimeUtils.now()
        ));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiError> handleDomainException(final DomainException ex) {
        return ResponseEntity.status(HttpStatus.valueOf(ex.getStatusCode())).body(ApiError.from(ex.getMessage(), ex.getStatusCode(), TimeUtils.now()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(final NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(ex.getMessage(), HttpStatus.NOT_FOUND.value(), TimeUtils.now()));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApiError> handleMissingServletRequestPartException(final MissingServletRequestPartException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError.from(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), TimeUtils.now()));
    }

    @ExceptionHandler(IdempotencyKeyUnsupportedMethodException.class)
    public ResponseEntity<ApiError> handleIdempotencyKeyUnsupportedMethodException(final IdempotencyKeyUnsupportedMethodException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ApiError.from(ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED.value(), TimeUtils.now()));
    }

    @ExceptionHandler(IdempotencyKeyRequiredException.class)
    public ResponseEntity<ApiError> handleIdempotencyKeyRequiredException(final IdempotencyKeyRequiredException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError.from(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), TimeUtils.now()));
    }

    @ExceptionHandler(IdempotencyKeyAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleIdempotencyKeyAlreadyExistsException(final IdempotencyKeyAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiError.from(ex.getMessage(), HttpStatus.CONFLICT.value(), TimeUtils.now()));
    }

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<ApiError> handleTooManyRequestsException(final TooManyRequestsException ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ApiError.from(ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS.value(), TimeUtils.now()));
    }
}
