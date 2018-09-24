package ch.tbmelabs.tv.core.authorizationserver.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.zaxxer.hikari.HikariDataSource;
import ch.tbmelabs.serverconstants.spring.SpringApplicationProfileConstants;

@Configuration
@Profile({SpringApplicationProfileConstants.TEST})
public class FlywayTestDatasourceConfiguration {

  private HikariDataSource dataSource;

  public FlywayTestDatasourceConfiguration(HikariDataSource hikariDataSource) {
    this.dataSource = hikariDataSource;
  }

  @Bean
  public Flyway flyway() {
    final Flyway flyway = new Flyway();

    flyway.setBaselineOnMigrate(true);
    flyway.setDataSource(dataSource);

    return flyway;
  }
}
