package ch.tbmelabs.authorizationserver.test.security.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import ch.tbmelabs.authorizationserver.domain.User;
import ch.tbmelabs.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;

public class SigninEndpointIntTest extends AbstractOAuth2AuthorizationServerContextAwareTest {

  private static final String SIGNIN_PROCESSING_URL = "/signin";
  private static final String USERNAME_PARAMETER_NAME = "username";
  private static final String PASSWORD_PARAMETER_NAME = "password";

  private static final String VALID_USERNAME = "valid_username";
  private static final String VALID_PASSWORD = "valid_password";

  private static final String UNAUTHORIZED_MESSAGE = "Unauthorized";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserCRUDRepository userRepository;

  @Before
  public void beforeTestSetup() {
    final User testUser = User.builder().username(VALID_USERNAME).email(VALID_USERNAME + "@tbme.tv")
        .password(VALID_PASSWORD).isBlocked(false).isEnabled(true).build();

    userRepository.save(testUser);
  }

  @Test
  public void loginProcessingWithInvalidUsernameShouldFail() throws Exception {
    String errorMessage = mockMvc
        .perform(post(SIGNIN_PROCESSING_URL).with(csrf()).param(USERNAME_PARAMETER_NAME, "invalid")
            .param(PASSWORD_PARAMETER_NAME, VALID_PASSWORD))
        .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value())).andReturn()
        .getResponse().getErrorMessage();

    assertThat(errorMessage).isEqualTo(UNAUTHORIZED_MESSAGE);
  }

  @Test
  public void loginProcessingWithInvalidPasswordShoulFail() throws Exception {
    String errorMessage = mockMvc
        .perform(
            post(SIGNIN_PROCESSING_URL).with(csrf()).param(USERNAME_PARAMETER_NAME, VALID_USERNAME)
                .param(PASSWORD_PARAMETER_NAME, "invalid"))
        .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value())).andReturn()
        .getResponse().getErrorMessage();

    assertThat(errorMessage).isEqualTo(UNAUTHORIZED_MESSAGE);
  }

  @Test
  public void loginProcessingWithValidCredentialsShouldSucceed() throws Exception {
    String redirectUrl = mockMvc
        .perform(
            post(SIGNIN_PROCESSING_URL).with(csrf()).param(USERNAME_PARAMETER_NAME, VALID_USERNAME)
                .param(PASSWORD_PARAMETER_NAME, VALID_PASSWORD))
        .andDo(print()).andExpect(status().is(HttpStatus.FOUND.value())).andReturn().getResponse()
        .getRedirectedUrl();

    assertThat(redirectUrl).isEqualTo("/");
  }

  @Test
  public void loginProcessingShouldFailIfUserIsDisabled() throws Exception {
    final User disabledUser = User.builder().username("blocked_user").email("blocked_user@tbme.tv")
        .password(VALID_PASSWORD).isBlocked(false).isEnabled(false).build();

    userRepository.save(disabledUser);


    String errorMessage = mockMvc
        .perform(post(SIGNIN_PROCESSING_URL).with(csrf())
            .param(USERNAME_PARAMETER_NAME, disabledUser.getUsername())
            .param(PASSWORD_PARAMETER_NAME, disabledUser.getPassword()))
        .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value())).andReturn()
        .getResponse().getErrorMessage();

    assertThat(errorMessage).isEqualTo(UNAUTHORIZED_MESSAGE);
  }

  @Test
  public void loginProcessingShouldFailIfUserIsBlocked() throws Exception {
    final User blockedUser = User.builder().username("blocked_user").email("blocked_user@tbme.tv")
        .password(VALID_PASSWORD).isBlocked(true).isEnabled(true).build();

    userRepository.save(blockedUser);

    String errorMessage = mockMvc
        .perform(post(SIGNIN_PROCESSING_URL).with(csrf())
            .param(USERNAME_PARAMETER_NAME, blockedUser.getUsername())
            .param(PASSWORD_PARAMETER_NAME, blockedUser.getPassword()))
        .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value())).andReturn()
        .getResponse().getErrorMessage();

    assertThat(errorMessage).isEqualTo(UNAUTHORIZED_MESSAGE);
  }
}
