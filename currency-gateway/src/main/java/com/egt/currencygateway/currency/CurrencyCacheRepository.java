package com.egt.currencygateway.currency;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CurrencyCacheRepository
{
    private static final Logger logger = Logger.getLogger(CurrencyCacheRepository.class.getName());
    private final Map<Integer, String> byId;
    private final Map<String, Integer> byCode;

    private final CurrencyRepository currencyRepository;

    public CurrencyCacheRepository(final CurrencyRepository currencyRepository)
    {
        this.currencyRepository = currencyRepository;
        byId = new HashMap<>();
        byCode = new HashMap<>();

        load();
    }


    public Integer getCurrencyIdByCode(final String currencyCode)
    {
        final Optional<Integer> currencyId = Optional.ofNullable(byCode.get(currencyCode));

        if (currencyId.isPresent())
        {
            return currencyId.get();
        }

        final Optional<Integer> currencyIdFromDb = currencyRepository.getCurrencyIdByCode(currencyCode);

        if (currencyIdFromDb.isPresent())
        {
            byCode.put(currencyCode, currencyIdFromDb.get());
            byId.put(currencyIdFromDb.get(), currencyCode);
            return currencyIdFromDb.get();
        }

        final Integer newCurrencyId = currencyRepository.save(currencyCode);

        byCode.put(currencyCode, newCurrencyId);
        byId.put(newCurrencyId, currencyCode);

        return newCurrencyId;
    }

    public String getCurrencyCodeById(final Integer currencyId)
    {
        final Optional<String> currencyCode = Optional.ofNullable(byId.get(currencyId));

        if (currencyCode.isPresent())
        {
            return currencyCode.get();
        }

        final Optional<String> currencyCodeFromDb = currencyRepository.getCurrencyCodeById(currencyId);

        if (currencyCodeFromDb.isPresent())
        {
            byCode.put(currencyCodeFromDb.get(), currencyId);
            byId.put(currencyId, currencyCodeFromDb.get());
            return currencyCodeFromDb.get();
        }

        throw new IllegalArgumentException("Currency with id " + currencyId + " does not exist");
    }


    private void load()
    {
        final var currencies = currencyRepository.getAllCurrencies();

        currencies.forEach((id, code) -> {
            byId.put(id, code);
            byCode.put(code, id);
        });

        logger.info("Loaded " + currencies.size() + " currencies");
    }


}
