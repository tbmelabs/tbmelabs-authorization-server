package ch.tbmelabs.authorizationserver.test.web.oauth2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.tbmelabs.authorizationserver.domain.Role;
import ch.tbmelabs.authorizationserver.domain.User;
import ch.tbmelabs.authorizationserver.domain.dto.RoleDTO;
import ch.tbmelabs.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.authorizationserver.service.domain.UserService;
import ch.tbmelabs.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import ch.tbmelabs.serverconstants.security.UserRoleEnum;
import java.util.Collections;
import java.util.HashSet;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

public class PrincipalEndpointIntTest extends AbstractOAuth2AuthorizationServerContextAwareTest {

  private static final String ME_ENDPOINT = "/me";
  private static final String USER_ENDPOINT = "/user";
  private static final String PROFILE_ENDPOINT = "/profile";

  private static final String USERNAME = "principal_username";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserService userService;

  private User testUser;

  @Before
  public void beforeTestSetUp() {
    final UserDTO userDTO = UserDTO.builder().username(USERNAME).email(USERNAME + "@tbme.tv")
      .password("valid_password").isBlocked(false).isEnabled(true)
      .roles(new HashSet<>(Collections
        .singletonList(RoleDTO.builder().name(UserRoleEnum.USER.getAuthority()).build())))
      .build();

    testUser = userService.save(userDTO);
  }

  @Test
  public void meEndpointShouldReturnCorrectAuthentication() throws Exception {
    runJsonCredentialAssertChain(
      new JSONObject(mockMvc.perform(get(ME_ENDPOINT).with(csrf()).with(user(USERNAME)))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse()
        .getContentAsString()));
  }

  @Test
  public void userEndpointShouldReturnCorrectAuthentication() throws Exception {
    runJsonCredentialAssertChain(
      new JSONObject(mockMvc.perform(get(USER_ENDPOINT).with(csrf()).with(user(USERNAME)))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse()
        .getContentAsString()));
  }

  private void runJsonCredentialAssertChain(JSONObject jsonCredential) throws JSONException {
    assertThat(jsonCredential.getString("login")).isEqualTo(USERNAME);
    assertThat(jsonCredential.getString("email")).isEqualTo(USERNAME + "@tbme.tv");
  }

  @Test
  public void profileEndpointShouldReturnCorrectUserDTO() throws Exception {
    JSONObject jsonUserRepresentation =
      new JSONObject(mockMvc.perform(get(PROFILE_ENDPOINT).with(csrf()).with(user(USERNAME)))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse()
        .getContentAsString());

    assertThat(jsonUserRepresentation.getLong("created"))
      .isEqualTo(testUser.getCreated().getTime());
    assertThat(jsonUserRepresentation.getLong("lastUpdated"))
      .isEqualTo(testUser.getLastUpdated().getTime());
    assertThat(jsonUserRepresentation.getLong("id")).isEqualTo(testUser.getId());
    assertThat(jsonUserRepresentation.getString("username")).isEqualTo(USERNAME);
    assertThat(jsonUserRepresentation.getString("email")).isEqualTo(USERNAME + "@tbme.tv");

    assertThatThrownBy(() -> jsonUserRepresentation.getString("password"))
      .isInstanceOf(JSONException.class).hasMessage("No value for password");

    assertThatThrownBy(() -> jsonUserRepresentation.getString("confirmation"))
      .isInstanceOf(JSONException.class).hasMessage("No value for confirmation");

    assertThat(jsonUserRepresentation.getBoolean("isEnabled")).isEqualTo(true);
    assertThat(jsonUserRepresentation.getBoolean("isBlocked")).isEqualTo(false);

    assertThat(jsonUserRepresentation.getJSONArray("roles").length()).isEqualTo(1);

    JSONObject actualRole = jsonUserRepresentation.getJSONArray("roles").getJSONObject(0);
    Role expectedRole = testUser.getRoles().iterator().next().getRole();
    assertThat(actualRole.getLong("created")).isEqualTo(expectedRole.getCreated().getTime());
    assertThat(actualRole.getLong("lastUpdated"))
      .isEqualTo(expectedRole.getLastUpdated().getTime());
    assertThat(actualRole.getLong("id")).isEqualTo(expectedRole.getId());
    assertThat(actualRole.getString("name")).isEqualTo(expectedRole.getName());
    assertThat(actualRole.getString("authority")).isEqualTo(expectedRole.getAuthority());
  }
}
