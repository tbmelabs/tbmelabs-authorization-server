package ch.tbmelabs.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.authorizationserver.configuration.SpELConfiguration;
import ch.tbmelabs.authorizationserver.security.spel.SecurityEvaluationContextExtension;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;

public class SpELConfigurationTest {

  @Test
  public void spelConfigurationShouldBeAnnotated() {
    assertThat(SpELConfiguration.class).hasAnnotation(Configuration.class);
  }

  @Test
  public void spelConfigurationShouldHavePublicConstructor() {
    assertThat(new SpELConfiguration()).isNotNull();
  }

  @Test
  public void securityExtensionShouldReturnAnEvaluationContextExtension() {
    assertThat(new SpELConfiguration().securityExtension())
      .isInstanceOf(SecurityEvaluationContextExtension.class);
  }
}
