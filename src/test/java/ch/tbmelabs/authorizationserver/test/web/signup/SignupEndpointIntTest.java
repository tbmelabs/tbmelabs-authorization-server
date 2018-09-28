package ch.tbmelabs.authorizationserver.test.web.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.tbmelabs.authorizationserver.domain.Role;
import ch.tbmelabs.authorizationserver.domain.User;
import ch.tbmelabs.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import ch.tbmelabs.serverconstants.security.UserRoleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

public class SignupEndpointIntTest extends AbstractOAuth2AuthorizationServerContextAwareTest {

  private static final String SIGNUP_ENDPOINT = "/signup/do-signup";
  private static final String PASSWORD_PARAMETER_NAME = "password";
  private static final String CONFIRMATION_PARAMETER_NAME = "confirmation";

  private static final String USER_VALIDATION_ERROR_MESSAGE =
    "An error occured. Please check your details!";
  private static final User unexistingUser = new User();
  private static User existingUser = new User();

  @Rule
  public ExpectedException passwordException = ExpectedException.none();

  @Rule
  public ExpectedException confirmationException = ExpectedException.none();

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private RoleCRUDRepository roleRepository;

  @Autowired
  private UserCRUDRepository userRepository;

  @BeforeClass
  public static void beforeClassSetUp() {
    existingUser.setUsername("Existing");
    existingUser.setEmail("existing.user@tbme.tv");
    existingUser.setPassword("Password$2018");
    existingUser.setConfirmation(existingUser.getPassword());

    unexistingUser.setUsername("Unexisting");
    unexistingUser.setEmail("unexisting.user@tbme.tv");
    unexistingUser.setPassword("Password$2018");
    unexistingUser.setConfirmation(unexistingUser.getPassword());
  }

  @Before
  public void beforeTestSetUp() {
    if (!roleRepository.findByName(UserRoleEnum.USER.getAuthority()).isPresent()) {
      roleRepository.save(new Role(UserRoleEnum.USER.getAuthority()));
    }

    existingUser = userRepository.save(existingUser);

    Optional<User> checkUnexistingUser;
    if ((checkUnexistingUser =
      userRepository.findByUsernameIgnoreCase(unexistingUser.getUsername())).isPresent()) {
      userRepository.delete(checkUnexistingUser.get());
    }
  }

  @Test
  public void postToSignupEndpointWithExistingUsernameOrEmailShouldFail() {
    assertThatThrownBy(() -> mockMvc
      .perform(post(SIGNUP_ENDPOINT).with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(existingUser)))
      .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
      .isInstanceOf(NestedServletException.class)
      .hasCauseInstanceOf(IllegalArgumentException.class)
      .hasStackTraceContaining(USER_VALIDATION_ERROR_MESSAGE);
  }

  @Test
  public void postToSignupEndpointShouldCreateUser() throws Exception {
    JSONObject createdJsonUser = new JSONObject(mockMvc
      .perform(post(SIGNUP_ENDPOINT).with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .content(new JSONObject(new ObjectMapper().writeValueAsString(unexistingUser))
          .put(PASSWORD_PARAMETER_NAME, unexistingUser.getConfirmation())
          .put(CONFIRMATION_PARAMETER_NAME, unexistingUser.getConfirmation()).toString()))
      .andDo(print()).andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse()
      .getContentAsString());

    assertThat(userRepository.findByUsernameIgnoreCase(unexistingUser.getUsername())).isNotNull();

    Integer id = createdJsonUser.getInt("id");
    assertThat(id).isNotNull().isPositive();

    Long created = createdJsonUser.getLong("created");
    assertThat(created).isNotNull().isPositive();

    Long lastUpdated = createdJsonUser.getLong("lastUpdated");
    assertThat(lastUpdated).isNotNull().isPositive();

    String username = createdJsonUser.getString("username");
    assertThat(username).isEqualTo(unexistingUser.getUsername());

    String email = createdJsonUser.getString("email");
    assertThat(email).isEqualTo(unexistingUser.getEmail());

    passwordException.expect(JSONException.class);
    assertThat(createdJsonUser.getString("password")).isNullOrEmpty();

    confirmationException.expect(JSONException.class);
    assertThat(createdJsonUser.getString("confirmation")).isNullOrEmpty();

    Boolean isEnabled = createdJsonUser.getBoolean("isEnabled");
    assertThat(isEnabled).isTrue();

    Boolean isBlocked = createdJsonUser.getBoolean("isBlocked");
    assertThat(isBlocked).isFalse();

    assertThat(createdJsonUser.getJSONArray("grantedAuthorities").length()).isEqualTo(1);
    assertThat(createdJsonUser.getJSONArray("grantedAuthorities").getString(0))
      .isEqualTo(UserRoleEnum.USER.getAuthority());
  }
}
