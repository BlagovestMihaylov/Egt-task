package com.egt.currencygateway.fixer.gateway;

import com.egt.currencygateway.fixer.model.ExchangeRateResponse;
import com.egt.currencygateway.fixer.properties.FixerApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class FixerGateway
{
    private static final Logger logger = Logger.getLogger(FixerGateway.class.getName());
    private static final String AUTHORIZATION = "access_key";
    private static final String BASE_CURRENCY_PARAM = "base";
    private final FixerApiProperties fixerApiProperties;
    private final RestTemplate restTemplate;

    public FixerGateway(final RestTemplate restTemplate, final FixerApiProperties fixerApiProperties)
    {
        this.fixerApiProperties = fixerApiProperties;

        this.restTemplate = restTemplate;
    }

    public ExchangeRateResponse getLatestRates(final Optional<String> baseCurrency)
    {
        String url = UriComponentsBuilder.fromHttpUrl(fixerApiProperties.getBaseUrl())
                                              .path(fixerApiProperties.getLatestExchangesEndpoint())
                                              .queryParam(AUTHORIZATION, fixerApiProperties.getApiKey())
                                              .queryParam(BASE_CURRENCY_PARAM,
                                                          baseCurrency.orElse(fixerApiProperties.getBaseCurrency()))
                                              .build()
                                              .toUriString();

        logger.info("Sending request to: " + url);

        return restTemplate.getForObject(url, ExchangeRateResponse.class);
    }

}
