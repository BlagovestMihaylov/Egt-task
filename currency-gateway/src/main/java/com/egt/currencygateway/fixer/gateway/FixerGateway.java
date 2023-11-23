package com.egt.currencygateway.fixer.gateway;

import com.egt.currencygateway.fixer.model.ExchangeRateResponse;
import com.egt.currencygateway.fixer.properties.FixerApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class FixerGateway
{
    private final static String AUTHORIZATION = "access_key";
    private final static String BASE_CURRENCY_PARAM = "base";
    private final FixerApiProperties fixerApiProperties;
    private final RestTemplate restTemplate;

    @Autowired
    public FixerGateway(final RestTemplate restTemplate, final FixerApiProperties fixerApiProperties)
    {
        this.fixerApiProperties = fixerApiProperties;

        this.restTemplate = restTemplate;
    }

    public ExchangeRateResponse getLatestRates(final Optional<String> baseCurrency)
    {
        String finalUrl = UriComponentsBuilder.fromHttpUrl(fixerApiProperties.getBaseUrl())
                                              .path(fixerApiProperties.getLatestExchangesEndpoint())
                                              .queryParam(AUTHORIZATION, fixerApiProperties.getApiKey())
                                              .queryParam(BASE_CURRENCY_PARAM,
                                                          baseCurrency.orElse(fixerApiProperties.getBaseCurrency()))
                                              .build()
                                              .toUriString();

        return restTemplate.getForObject(finalUrl, ExchangeRateResponse.class);
    }

}
