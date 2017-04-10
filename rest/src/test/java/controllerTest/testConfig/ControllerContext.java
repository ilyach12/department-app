package controllerTest.testConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan({"com.chaikovsky.controller", "com.chaikovsky.dao", "com.chaikovsky.service"})
@Profile("controller")
public class ControllerContext {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("testDb/create-db.sql")
                .addScript("testDb/insert-data.sql")
                .build();
    }

    @Bean
    public NamedParameterJdbcTemplate setJdbc(){
        return new NamedParameterJdbcTemplate(dataSource());
    }
}
