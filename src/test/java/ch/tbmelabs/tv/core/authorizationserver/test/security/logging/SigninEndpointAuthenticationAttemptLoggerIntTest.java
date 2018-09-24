package ch.tbmelabs.tv.core.authorizationserver.test.security.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog;
import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog.AUTHENTICATION_STATE;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;

public class SigninEndpointAuthenticationAttemptLoggerIntTest
    extends AbstractOAuth2AuthorizationServerContextAwareTest {

  private static final String SIGNIN_PROCESSING_URL = "/signin";
  private static final String USERNAME_PARAMETER_NAME = "username";
  private static final String PASSWORD_PARAMETER_NAME = "password";

  private static final String VALID_USERNAME = "valid_username";
  private static final String VALID_PASSWORD = "valid_password";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AuthenticationLogCRUDRepository authenticationLogRepository;

  @Autowired
  private UserCRUDRepository userRepository;

  @Before
  public void beforeTestSetup() {
    final User testUser = User.builder().username(VALID_USERNAME).email(VALID_USERNAME + "@tbme.tv")
        .password(VALID_PASSWORD).isBlocked(false).isEnabled(true).build();

    userRepository.save(testUser);
  }

  @Test
  public void loginWithInvalidUsernameShouldNotBeRegistered() throws Exception {
    mockMvc
        .perform(post(SIGNIN_PROCESSING_URL).with(csrf()).param(USERNAME_PARAMETER_NAME, "invalid")
            .param(PASSWORD_PARAMETER_NAME, "invalid"))
        .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));

    assertThat(authenticationLogRepository.findAll()).isNullOrEmpty();
  }

  @Test
  public void loginWithValidUsernameAndInvalidPasswordShouldBeRegistered() throws Exception {
    mockMvc
        .perform(
            post(SIGNIN_PROCESSING_URL).with(csrf()).param(USERNAME_PARAMETER_NAME, VALID_USERNAME)
                .param(PASSWORD_PARAMETER_NAME, "invalid"))
        .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));

    List<AuthenticationLog> logs =
        (ArrayList<AuthenticationLog>) authenticationLogRepository.findAll();

    assertThat(logs).hasSize(1).extracting("state").containsExactly(AUTHENTICATION_STATE.NOK);
    assertThat(logs).extracting("user").extracting("username").containsExactly(VALID_USERNAME);
  }

  @Test
  public void loginWithValidUserShouldBeRegistered() throws Exception {
    String redirectUrl = mockMvc
        .perform(
            post(SIGNIN_PROCESSING_URL).with(csrf()).param(USERNAME_PARAMETER_NAME, VALID_USERNAME)
                .param(PASSWORD_PARAMETER_NAME, VALID_PASSWORD))
        .andDo(print()).andExpect(status().is(HttpStatus.FOUND.value())).andReturn().getResponse()
        .getRedirectedUrl();

    assertThat(redirectUrl).isEqualTo("/");

    List<AuthenticationLog> logs =
        (ArrayList<AuthenticationLog>) authenticationLogRepository.findAll();

    assertThat(logs).hasSize(1).extracting("state").containsExactly(AUTHENTICATION_STATE.OK);
    assertThat(logs).extracting("user").extracting("username").containsExactly(VALID_USERNAME);
  }
}
