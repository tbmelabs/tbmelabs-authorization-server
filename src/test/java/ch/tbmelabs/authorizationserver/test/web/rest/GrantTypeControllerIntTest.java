package ch.tbmelabs.authorizationserver.test.web.rest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.tbmelabs.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import ch.tbmelabs.serverconstants.security.UserRoleConstants;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

public class GrantTypeControllerIntTest extends AbstractOAuth2AuthorizationServerContextAwareTest {

  @Value("${spring.data.rest.base-path}/grant-types")
  private String grantTypesEndpoint;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser(username = "GrantTypeControllerIntTestUser",
    authorities = {UserRoleConstants.SERVER_ADMIN})
  public void getGrantTypesEndpointIsAccessibleToServerAdmins() throws Exception {
    mockMvc.perform(get(grantTypesEndpoint).with(csrf())).andDo(print())
      .andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  @WithMockUser(username = "GrantTypeControllerIntTestUser",
    authorities = {UserRoleConstants.SERVER_SUPPORT})
  public void getGrantTypeEndpointIsNotAccessibleToNonServerAdmins() throws Exception {
    mockMvc.perform(get(grantTypesEndpoint).with(csrf())).andDo(print())
      .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  }
}
