package com.egt.currencygateway.rates.repository;

import com.egt.currencygateway.rates.model.Rate;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

@Repository
@DependsOn({"flyway", "flywayInitializer"})
public class RatesRepository extends NamedParameterJdbcDaoSupport
{
    private final RateRowMapper rateRowMapper;

    public RatesRepository(final DataSource dataSource, final RateRowMapper rateRowMapper)
    {
        this.rateRowMapper = rateRowMapper;

        setDataSource(dataSource);
    }

    //todo test sql
    public List<Rate> getLastRates(final String baseCurrencyCode)
    {
        final String sql = """
                SELECT DISTINCT base_currency_id, target_currency_id, rate, timestamp
                FROM rates
                WHERE base_currency_id = :base_currency_id
                ORDER BY timestamp DESC
                """;

        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("base_currency_id", baseCurrencyCode);

        return Objects.requireNonNull(getNamedParameterJdbcTemplate())
                      .query(sql, sqlParameterSource, (resultSet, i) -> rateRowMapper.mapRow(resultSet));
    }

    public List<Rate> getRatesForLastHours(final int baseCurrencyId, final int hours)
    {
        final String sql = """
                SELECT *
                FROM rates
                WHERE base_currency_id = :base_currency_id
                  AND timestamp >= NOW() - INTERVAL :hours HOUR;
                """;

        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("base_currency_id", baseCurrencyId)
                .addValue("hours", hours);

        return Objects.requireNonNull(getNamedParameterJdbcTemplate())
                      .query(sql, sqlParameterSource, (resultSet, i) -> rateRowMapper.mapRow(resultSet));
    }

    public void batchInsertRates(final List<Rate> rates)
    {
        final String sql = """
                INSERT INTO rates (base_currency_id, target_currency_id, rate, timestamp)
                VALUES (:base_currency_id, :target_currency_id, :rate, :timestamp);
                """;

        Objects.requireNonNull(getNamedParameterJdbcTemplate())
               .batchUpdate(sql, rates.stream()
                                      .map(rateRowMapper::toMapSqlParameterSource)
                                      .toArray(MapSqlParameterSource[]::new));
    }

}
