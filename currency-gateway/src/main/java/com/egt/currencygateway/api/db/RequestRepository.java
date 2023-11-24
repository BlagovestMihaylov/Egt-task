package com.egt.currencygateway.api.db;

import com.egt.currencygateway.api.model.RequestDTO;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Objects;

@Repository
@DependsOn({"flyway", "flywayInitializer"})
public class RequestRepository extends NamedParameterJdbcDaoSupport
{
    private final RequestRowMapper requestRowMapper;

    public RequestRepository(final DataSource dataSource,
                             final RequestRowMapper requestRowMapper)
    {
        this.requestRowMapper = requestRowMapper;
        setDataSource(dataSource);
    }

    public void save(final RequestDTO request)
    {
        final String sql = """
                INSERT INTO requests (id, currency, client_id, timestamp, query_period, service_name) VALUES (:id, :currency, :client_id, :timestamp, :query_period, :service_name)
                """;

        Objects.requireNonNull(getNamedParameterJdbcTemplate())
               .update(sql, new MapSqlParameterSource("id", request.getId())
                       .addValue("currency", request.getCurrency())
                       .addValue("client_id", request.getClientId())
                       .addValue("timestamp", new Timestamp(request.getTimestamp()))
                       .addValue("query_period", request.getPeriod())
                       .addValue("service_name", request.getServiceName())
               );
    }

    public void complete(final String id,
                         final String serviceName)
    {
        final String sql = """
                UPDATE requests SET completed = 1 WHERE id = :id AND service_name = :service_name
                """;

        Objects.requireNonNull(getNamedParameterJdbcTemplate())
               .update(sql, new MapSqlParameterSource("id", id)
                       .addValue("service_name", serviceName)
               );
    }

}
