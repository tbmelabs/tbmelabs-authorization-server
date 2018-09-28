package ch.tbmelabs.authorizationserver.test.web.signup;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.tbmelabs.authorizationserver.domain.User;
import ch.tbmelabs.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import ch.tbmelabs.authorizationserver.test.domain.dto.UserDTOTest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class EmailUniqueCheckEndpointIntTest
  extends AbstractOAuth2AuthorizationServerContextAwareTest {

  private static final String EMAIL_UNIQUE_CHECK_ENDPOINT = "/signup/is-email-unique";
  private static final String EMAIL_PARAMETER_NAME = "email";

  private static final String VALID_EMAIL = "valid.email@tbme.tv";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AuthenticationLogCRUDRepository authenticationLogRepository;

  @Autowired
  private UserCRUDRepository userRepository;

  private User testUser;

  @Before
  public void beforeTestSetUp() {
    authenticationLogRepository.deleteAll();

    User newUser = UserDTOTest.createTestUser();

    testUser = userRepository.save(newUser);
  }

  @Test
  public void registrationWithExistingEmailShouldFailValidation() throws Exception {
    mockMvc
      .perform(post(EMAIL_UNIQUE_CHECK_ENDPOINT).with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(new JSONObject().put(EMAIL_PARAMETER_NAME, testUser.getEmail()).toString()))
      .andDo(print()).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  public void registrationWithNewEmailShouldPassValidation() throws Exception {
    mockMvc
      .perform(
        post(EMAIL_UNIQUE_CHECK_ENDPOINT).with(csrf()).contentType(MediaType.APPLICATION_JSON)
          .content(new JSONObject().put(EMAIL_PARAMETER_NAME, VALID_EMAIL).toString()))
      .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }
}
