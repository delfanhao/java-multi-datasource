package com.abc.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class MultiDataSourceConfig {
    @Primary
    @Bean(name = "remoteDbProp")
    @ConfigurationProperties(prefix = "spring.datasource.remote")
    public DataSourceProperties remoteDbProp() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "remoteDb")
    public DataSource remoteDb(@Qualifier("remoteDbProp") DataSourceProperties dsProp) {
        return dsProp.initializeDataSourceBuilder().build();
    }

    @Bean(name = "localDbProp")
    @ConfigurationProperties(prefix = "spring.datasource.local")
    public DataSourceProperties localDbProp() {
        return new DataSourceProperties();
    }

    @Bean("localDb")
    public DataSource localDb(@Qualifier("localDbProp") DataSourceProperties dsProp) {
        return dsProp.initializeDataSourceBuilder().build();
    }
}
