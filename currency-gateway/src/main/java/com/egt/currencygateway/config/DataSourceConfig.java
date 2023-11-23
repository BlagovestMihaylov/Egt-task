package com.egt.currencygateway.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig
{

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource()
    {
        return new HikariDataSource();
    }
}