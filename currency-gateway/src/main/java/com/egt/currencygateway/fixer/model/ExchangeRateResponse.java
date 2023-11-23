package com.egt.currencygateway.fixer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record ExchangeRateResponse(
        @JsonProperty("success") boolean success,
        @JsonProperty("timestamp") long timestamp,
        @JsonProperty("base") String base,
        @JsonProperty("date") @JsonFormat(pattern = "yyyy-MM-dd") String date,
        @JsonProperty("rates") Map<String, Double> rates
) {
}