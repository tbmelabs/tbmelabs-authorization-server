package ch.tbmelabs.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.authorizationserver.configuration.AuthenticationManagerConfiguration;
import ch.tbmelabs.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

public class AuthenticationManagerConfigurationIntTest
  extends AbstractOAuth2AuthorizationServerContextAwareTest {

  @Autowired
  private AuthenticationManagerConfiguration configuration;

  @Autowired
  private AuthenticationManager bean;

  @Test
  public void primaryRegisteredBeanShouldEqualConfiguredAuthenticationManager() throws Exception {
    assertThat(configuration.authenticationManager()).isEqualTo(bean);
  }
}
