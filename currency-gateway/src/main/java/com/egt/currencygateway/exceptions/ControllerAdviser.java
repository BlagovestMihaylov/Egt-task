package com.egt.currencygateway.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerAdviser
{
    @ExceptionHandler(value
            = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request)
    {
        final String bodyOfResponse = "This should be application specific";
        return ResponseEntity.badRequest()
                             .body(bodyOfResponse);
    }

    @ExceptionHandler(value
            = {MissingCurrencyException.class})
    protected ResponseEntity<Object> handleMissingCurrency(final RuntimeException ex, final WebRequest request)
    {
        return ResponseEntity.notFound()
                             .build();
    }

    @ExceptionHandler(value
            = {ExchangeNotFoundException.class})
    protected ResponseEntity<Object> handleExchangeNotFound(final RuntimeException ex, final WebRequest request)
    {
        return ResponseEntity.notFound()
                             .build();
    }

    @ExceptionHandler(value
            = {DuplicateRequestException.class})
    protected ResponseEntity<Object> handleDuplicateRequest(final RuntimeException ex, final WebRequest request)
    {
        return ResponseEntity.status(429)
                             .build();
    }

}
