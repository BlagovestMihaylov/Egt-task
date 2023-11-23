package com.egt.currencygateway.fixer.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("fixer")
public class FixerApiProperties {

    private String baseUrl;
    private String apiKey;
    private int refreshRateSeconds;
    private String latestExchangesEndpoint;
    private String currencyBaseParam;
    private String baseCurrency;

    public FixerApiProperties() {
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public int getRefreshRateSeconds() {
        return refreshRateSeconds;
    }

    public void setRefreshRateSeconds(int refreshRateSeconds) {
        this.refreshRateSeconds = refreshRateSeconds;
    }

    public String getLatestExchangesEndpoint() {
        return latestExchangesEndpoint;
    }

    public void setLatestExchangesEndpoint(String latestExchangesEndpoint) {
        this.latestExchangesEndpoint = latestExchangesEndpoint;
    }

    public String getCurrencyBaseParam() {
        return currencyBaseParam;
    }

    public void setCurrencyBaseParam(String currencyBaseParam) {
        this.currencyBaseParam = currencyBaseParam;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }
}
