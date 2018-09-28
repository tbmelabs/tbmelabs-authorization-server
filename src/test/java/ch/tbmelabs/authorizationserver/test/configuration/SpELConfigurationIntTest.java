package ch.tbmelabs.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.authorizationserver.configuration.SpELConfiguration;
import ch.tbmelabs.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.spi.EvaluationContextExtension;

public class SpELConfigurationIntTest extends AbstractOAuth2AuthorizationServerContextAwareTest {

  @Autowired
  private SpELConfiguration configuration;

  @Autowired
  private EvaluationContextExtension bean;

  @Test
  public void primaryRegisteredBeanShouldEqualConfiguredEvaluationContextExtension() {
    assertThat(configuration.securityExtension()).isEqualTo(bean);
  }
}
