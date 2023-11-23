package com.egt.currencygateway.rates.model;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public record Rate(
        String baseCurrency,
        String targetCurrency,
        BigDecimal rate,
        Timestamp timestamp
) implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;
}

