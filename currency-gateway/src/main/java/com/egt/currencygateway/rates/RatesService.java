package com.egt.currencygateway.rates;

import com.egt.currencygateway.currency.CurrencyCacheRepository;
import com.egt.currencygateway.exceptions.ExchangeNotFoundException;
import com.egt.currencygateway.fixer.model.ExchangeRateResponse;
import com.egt.currencygateway.fixer.service.CurrencyExchangeService;
import com.egt.currencygateway.rates.model.Rate;
import com.egt.currencygateway.rates.repository.RatesRedisRepository;
import com.egt.currencygateway.rates.repository.RatesRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

@Service
public class RatesService
{
    private static final Logger logger = Logger.getLogger(RatesService.class.getName());
    private final CurrencyExchangeService currencyExchangeService;
    private final RatesRedisRepository ratesRedisRepository;
    private final RatesRepository ratesRepository;
    private final CurrencyCacheRepository currencyCacheRepository;

    public RatesService(final CurrencyExchangeService currencyExchangeService,
                        final RatesRedisRepository ratesRedisRepository,
                        final RatesRepository ratesRepository,
                        final CurrencyCacheRepository currencyCacheRepository)

    {
        this.currencyExchangeService = currencyExchangeService;
        this.ratesRedisRepository = ratesRedisRepository;
        this.ratesRepository = ratesRepository;
        this.currencyCacheRepository = currencyCacheRepository;

        updateRates();
    }


    public List<Rate> getRates(final String baseCurrency, final int hoursAgo)
    {
        final int currencyId = currencyCacheRepository.getCurrencyIdByCode(baseCurrency);

        return ratesRepository.getRatesForLastHours(currencyId, hoursAgo);
    }

    public List<Rate> getRates(final String baseCurrency)
    {
        final List<Rate> cacheRates = ratesRedisRepository.retrieveRates(baseCurrency);

        if (!CollectionUtils.isEmpty(cacheRates))
        {
            return cacheRates;
        }

        logger.info("Rates not found in cache. Retrieving from DB");
        final List<Rate> dbRates = ratesRepository.getLastRates(baseCurrency);

        if (!CollectionUtils.isEmpty(dbRates))
        {
            ratesRedisRepository.storeRates(baseCurrency, dbRates);
            return dbRates;
        }

        logger.info("Rates not found in DB. Retrieving from fixer");
        final ExchangeRateResponse exchangeRateResponse = currencyExchangeService.getExchangeRate(baseCurrency);

        if (!exchangeRateResponse.success())
        {
            throw new ExchangeNotFoundException("Exchange not found for currency: " + baseCurrency);
        }

        updateRates(exchangeRateResponse);
        return ratesRedisRepository.retrieveRates(baseCurrency);
    }


    @Scheduled(cron = "${fixer.update-rates-cron}")
    private void updateRates()
    {
        final ExchangeRateResponse exchangeRateResponse = currencyExchangeService.getExchangeRate();

        updateRates(exchangeRateResponse);
    }

    private void updateRates(final ExchangeRateResponse exchangeRateResponse)
    {
        final List<Rate> rates = mapToRates(exchangeRateResponse);

        ratesRedisRepository.storeRates(exchangeRateResponse.base(), rates);

        ratesRepository.batchInsertRates(rates);

        logger.info("Rates updated. Size: " + rates.size() + " Base currency: " + exchangeRateResponse.base() + " Timestamp: " + exchangeRateResponse.timestamp());
    }

    private static List<Rate> mapToRates(final ExchangeRateResponse exchangeRateResponse)
    {
        return exchangeRateResponse.rates()
                                   .entrySet()
                                   .stream()
                                   .map(entry -> new Rate(exchangeRateResponse.base(),
                                                          entry.getKey(),
                                                          BigDecimal.valueOf(entry.getValue()),
                                                          Timestamp.from(Instant.ofEpochSecond(
                                                                  exchangeRateResponse.timestamp()))))
                                   .toList();
    }

}
