package com.burakkocak.casestudies.exchangeservice.Config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfiguration {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.jpa.hibernate.default_schema}")
    private String schema;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean(name = "flywayInitializer")
    public FlywayMigrationInitializer flywayMigrationInitializer() {
        Flyway flyway = Flyway.configure()
                .locations("classpath:/db/migration")
                .schemas(schema)
                .baselineOnMigrate(true)
                .baselineVersion("1")
                .dataSource(url, user, password)
                .load();
        return new FlywayMigrationInitializer(flyway);
    }
}
