package com.egt.currencygateway.rates.repository;

import com.egt.currencygateway.currency.CurrencyCacheRepository;
import com.egt.currencygateway.rates.model.Rate;
import org.flywaydb.core.internal.jdbc.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RateRowMapper implements RowMapper<Rate>
{

    private final CurrencyCacheRepository currencyCacheRepository;

    public RateRowMapper(CurrencyCacheRepository currencyCacheRepository)
    {
        this.currencyCacheRepository = currencyCacheRepository;
    }

    @Override
    public Rate mapRow(ResultSet resultSet)
    throws SQLException
    {
        return new Rate(
                currencyCacheRepository.getCurrencyCodeById(resultSet.getInt("base_currency_id")),
                currencyCacheRepository.getCurrencyCodeById(resultSet.getInt("target_currency_id")),
                resultSet.getBigDecimal("rate"),
                resultSet.getTimestamp("timestamp")
        );
    }


    public MapSqlParameterSource toMapSqlParameterSource(final Rate rate)
    {
        return new MapSqlParameterSource()
                .addValue("base_currency_id", currencyCacheRepository.getCurrencyIdByCode(rate.baseCurrency()))
                .addValue("target_currency_id", currencyCacheRepository.getCurrencyIdByCode(rate.targetCurrency()))
                .addValue("rate", rate.rate())
                .addValue("timestamp", rate.timestamp());
    }
}
