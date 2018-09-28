package ch.tbmelabs.authorizationserver.configuration;

import ch.tbmelabs.authorizationserver.security.spel.SecurityEvaluationContextExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.spi.EvaluationContextExtension;

@Configuration
public class SpELConfiguration {

  @Bean
  public EvaluationContextExtension securityExtension() {
    return new SecurityEvaluationContextExtension();
  }
}
