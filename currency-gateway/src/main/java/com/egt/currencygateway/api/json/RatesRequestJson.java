package com.egt.currencygateway.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RatesRequestJson(
        @JsonProperty("requestId") String requestId,
        @JsonProperty("currency") String currency,
        @JsonProperty("clientId") Long clientId,
        @JsonProperty("timestamp") Long timestamp,
        @JsonProperty("period") Integer period
)
{
}
