package com.egt.currencygateway.api.db;

import com.egt.currencygateway.api.model.RequestDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RequestRowMapper implements RowMapper<RequestDTO>
{
    @Override
    public RequestDTO mapRow(ResultSet rs, int rowNum)
    throws SQLException
    {
        return new RequestDTO(rs.getString("id"),
                              rs.getString("currency"),
                              rs.getLong("client_id"),
                              rs.getLong("timestamp"),
                              rs.getInt("query_period"),
                              rs.getString("service_name"));
    }
}
