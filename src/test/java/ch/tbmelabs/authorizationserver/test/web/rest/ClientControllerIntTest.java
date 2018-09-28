package ch.tbmelabs.authorizationserver.test.web.rest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.tbmelabs.authorizationserver.domain.Client;
import ch.tbmelabs.authorizationserver.domain.dto.AuthorityDTO;
import ch.tbmelabs.authorizationserver.domain.dto.ClientDTO;
import ch.tbmelabs.authorizationserver.domain.dto.GrantTypeDTO;
import ch.tbmelabs.authorizationserver.domain.dto.ScopeDTO;
import ch.tbmelabs.authorizationserver.domain.dto.mapper.ClientMapper;
import ch.tbmelabs.authorizationserver.domain.repository.ClientCRUDRepository;
import ch.tbmelabs.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import ch.tbmelabs.authorizationserver.test.domain.dto.ClientDTOTest;
import ch.tbmelabs.serverconstants.security.UserRoleConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

public class ClientControllerIntTest extends AbstractOAuth2AuthorizationServerContextAwareTest {

  @Value("${spring.data.rest.base-path}/clients")
  private String clientsEndpoint;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ClientMapper clientMapper;

  @Autowired
  private ClientCRUDRepository clientRepository;

  private ClientDTO testClientDTO = createTestClientDTO();

  public static ClientDTO createTestClientDTO() {
    Client client = ClientDTOTest.createTestClient();
    ClientDTO dto = new ClientDTO();
    dto.setId(client.getId());
    dto.setCreated(client.getCreated());
    dto.setLastUpdated(client.getLastUpdated());
    dto.setClientId(client.getClientId());
    dto.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
    dto.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
    dto.setRedirectUris(client.getRedirectUri().split(Client.REDIRECT_URI_SPLITTERATOR));

    dto.setGrantTypes(new HashSet<GrantTypeDTO>());

    dto.setAuthorities(new HashSet<AuthorityDTO>());

    dto.setScopes(new HashSet<ScopeDTO>());

    return dto;
  }

  @Test
  @WithMockUser(username = "ClientControllerIntTestUser",
    authorities = {UserRoleConstants.SERVER_ADMIN})
  public void postClientEndpointIsAccessibleToServerAdmins() throws Exception {
    mockMvc
      .perform(post(clientsEndpoint).with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(testClientDTO)))
      .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  @WithMockUser(username = "ClientControllerIntTestUser",
    authorities = {UserRoleConstants.SERVER_SUPPORT})
  public void postClientEndpointIsNotAccessibleToNonServerAdmins() throws Exception {
    mockMvc
      .perform(post(clientsEndpoint).with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(testClientDTO)))
      .andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  }

  @Test
  @WithMockUser(username = "ClientControllerIntTestUser",
    authorities = {UserRoleConstants.SERVER_ADMIN})
  public void getClientsEndpointIsAccessibleToServerAdmins() throws Exception {
    mockMvc.perform(get(clientsEndpoint).with(csrf())).andDo(print())
      .andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  @WithMockUser(username = "ClientControllerIntTestUser",
    authorities = {UserRoleConstants.SERVER_SUPPORT})
  public void getClientsEndpointIsNotAccessibleToNonServerAdmins() throws Exception {
    mockMvc.perform(get(clientsEndpoint).with(csrf())).andDo(print())
      .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  }

  @Test
  @WithMockUser(username = "ClientControllerIntTestUser",
    authorities = {UserRoleConstants.SERVER_ADMIN})
  public void putClientEndpointIsAccessibleToServerAdmins() throws Exception {
    testClientDTO = clientMapper.toDto(clientRepository.save(clientMapper.toEntity(testClientDTO)));

    mockMvc
      .perform(put(clientsEndpoint).with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(testClientDTO)))
      .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  @WithMockUser(username = "ClientControllerIntTestUser",
    authorities = {UserRoleConstants.SERVER_SUPPORT})
  public void putClientEndpointIsNotAccessibleToNonServerAdmins() throws Exception {
    mockMvc
      .perform(put(clientsEndpoint).with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(testClientDTO)))
      .andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  }

  @Test
  @WithMockUser(username = "ClientControllerIntTestUser",
    authorities = {UserRoleConstants.SERVER_ADMIN})
  public void deleteClientEndpointIsAccessibleToServerAdmins() throws Exception {
    testClientDTO = clientMapper.toDto(clientRepository.save(clientMapper.toEntity(testClientDTO)));

    mockMvc.perform(delete(clientsEndpoint + "/" + testClientDTO.getId()).with(csrf()))
      .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  @WithMockUser(username = "ClientControllerIntTestUser",
    authorities = {UserRoleConstants.SERVER_SUPPORT})
  public void deleteClientEndpointIsNotAccessibleToNonServerAdmins() throws Exception {
    testClientDTO = clientMapper.toDto(clientRepository.save(clientMapper.toEntity(testClientDTO)));

    mockMvc.perform(delete(clientsEndpoint + "/" + testClientDTO.getId()).with(csrf()))
      .andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  }
}
