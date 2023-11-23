package com.egt.currencygateway.fixer.service;

import com.egt.currencygateway.fixer.gateway.FixerGateway;
import com.egt.currencygateway.fixer.model.ExchangeRateResponse;
import com.egt.currencygateway.fixer.properties.FixerApiProperties;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyExchangeService
{
    private final FixerGateway fixerService;;
    public CurrencyExchangeService(final FixerGateway fixerService)
    {
        this.fixerService = fixerService;
    }

    public ExchangeRateResponse getExchangeRate(final String baseCurrency)
    {
        return fixerService.getLatestRates(Optional.of(baseCurrency));
    }

    public ExchangeRateResponse getExchangeRate()
    {
        return fixerService.getLatestRates(Optional.empty());
    }
}
