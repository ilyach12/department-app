package com.chaikovsky.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Configure connection for database. All database information
 * for connection is stored on db.properties file on classpath.
 */
@Configuration
@PropertySource("classpath:db.properties")
public class DataBaseConnection {

    @Resource
    private Environment env;

    @Value("classpath:db/db.sql")
    private org.springframework.core.io.Resource schemaScript;
    @Value("classpath:db/data.sql")
    private org.springframework.core.io.Resource dataScript;

    /**
     * {@code dataSource()} provides database connection.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getRequiredProperty("db.url"));
        dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
        dataSource.setUsername(env.getRequiredProperty("db.username"));
        dataSource.setPassword(env.getRequiredProperty("db.password"));
        DatabasePopulatorUtils.execute(databasePopulator(), dataSource);
        return dataSource;
    }

    /**
     * Inserts test data into Database.
     */
    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript);
        populator.addScript(dataScript);
        return populator;
    }
}
