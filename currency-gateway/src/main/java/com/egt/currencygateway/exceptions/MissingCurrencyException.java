package com.egt.currencygateway.exceptions;

public class MissingCurrencyException extends RuntimeException
{
    public MissingCurrencyException(final String message)
    {
        super(message);
    }
}
