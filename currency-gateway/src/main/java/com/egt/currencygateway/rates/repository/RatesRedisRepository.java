package com.egt.currencygateway.rates.repository;

import com.egt.currencygateway.rates.model.Rate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatesRedisRepository
{
    private final RedisTemplate<String, Object> redisTemplate;

    public RatesRedisRepository(RedisTemplate<String, Object> redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }

    public void storeRates(String baseCurrency, List<Rate> rates)
    {
        redisTemplate.opsForHash()
                     .put(baseCurrency, "rates", rates);
    }

    public List<Rate> retrieveRates(String baseCurrency)
    {
        return (List<Rate>) redisTemplate.opsForHash()
                                         .get(baseCurrency, "rates");
    }
}