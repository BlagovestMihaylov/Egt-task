package com.egt.currencygateway.exceptions;

public class ExchangeNotFoundException extends RuntimeException
{
    public ExchangeNotFoundException(String message)
    {
        super(message);
    }
}
