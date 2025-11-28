package com.miguel.jobnest.infrastructure.configuration;

import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.infrastructure.services.exceptions.JwtTokenValidationFailedException;
import com.miguel.jobnest.infrastructure.utils.ApiError;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        final List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

        return ResponseEntity.badRequest().body(ApiError.from(errors, HttpStatus.BAD_REQUEST.getReasonPhrase()));
    }

    @ExceptionHandler(JwtTokenValidationFailedException.class)
    public ResponseEntity<ApiError> handleJwtTokenValidationFailedException(JwtTokenValidationFailedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiError.from(
                Collections.singletonList(ex.getMessage()), HttpStatus.UNAUTHORIZED.getReasonPhrase()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex) {
        log.error("Handling unexpected exception", ex);
        return ResponseEntity.internalServerError().body(ApiError.from(
                Collections.singletonList("An unexpected error occurred"), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        ));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiError> handleDomainException(DomainException ex) {
        return ResponseEntity.status(HttpStatus.valueOf(ex.getStatusCode())).body(ApiError.from(
                Collections.singletonList(ex.getMessage()), HttpStatus.valueOf(ex.getStatusCode()).getReasonPhrase()
        ));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(
                Collections.singletonList(ex.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<ApiError> handleRequestNotPermitted(RequestNotPermitted ex) {
        log.info("Handling request not permitted exception due to rate limit exceed", ex);
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ApiError.from(
                Collections.singletonList("Too many requests occurred at the same time"), HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase()
        ));
    }
}
