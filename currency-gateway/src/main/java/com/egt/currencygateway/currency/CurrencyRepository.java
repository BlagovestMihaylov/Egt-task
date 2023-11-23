package com.egt.currencygateway.currency;

import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
@DependsOn({"flyway", "flywayInitializer"})
public class CurrencyRepository extends NamedParameterJdbcDaoSupport
{
    public CurrencyRepository(final DataSource dataSource)
    {
        setDataSource(dataSource);
    }


    public Integer save(final String currencyCode)
    {
        final String sql = """
                INSERT INTO n_currencies (code) VALUES (:code)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        Objects.requireNonNull(getNamedParameterJdbcTemplate())
               .update(sql, new MapSqlParameterSource("code", currencyCode), keyHolder,
                       new String[]{"id"}
               );

        return Objects.requireNonNull(keyHolder.getKey())
                      .intValue();
    }


    public Optional<Integer> getCurrencyIdByCode(final String currencyCode)
    {
        final String sql = """
                SELECT id
                FROM n_currencies
                WHERE code = :code;
                """;

        try
        {
            return Optional.ofNullable(Objects.requireNonNull(getNamedParameterJdbcTemplate())
                                              .queryForObject(sql, new MapSqlParameterSource("code", currencyCode),
                                                              Integer.class));
        }
        catch (EmptyResultDataAccessException e)
        {
            return Optional.empty();
        }
    }

    public Optional<String> getCurrencyCodeById(final Integer currency_id)
    {
        final String sql = """
                SELECT code
                FROM n_currencies
                WHERE id = :id;
                """;

        try
        {
            return Optional.ofNullable(Objects.requireNonNull(getNamedParameterJdbcTemplate())
                                              .queryForObject(sql, Map.of("id", currency_id), String.class));
        }
        catch (EmptyResultDataAccessException e)
        {
            return Optional.empty();
        }
    }

    public Map<Integer, String> getAllCurrencies()
    {
        final String sql = """
                SELECT id, code
                FROM n_currencies;
                """;

        final Map<Integer, String> currencies = new HashMap<>();
        Objects.requireNonNull(getNamedParameterJdbcTemplate())
               .query(sql, Collections.emptyMap(),
                      (rs, rowNum) -> currencies.put(rs.getInt("id"), rs.getString("code")));

        return currencies;
    }

}
