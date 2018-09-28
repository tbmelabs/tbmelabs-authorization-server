package ch.tbmelabs.authorizationserver.test.service.clientdetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.authorizationserver.domain.Client;
import ch.tbmelabs.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import ch.tbmelabs.authorizationserver.service.clientdetails.ClientDetailsImpl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.oauth2.provider.ClientDetails;

public class ClientDetailsImplTest {

  private static final String CLIENT_REDIRECT_URIS =
    "uirone" + ClientDetailsImpl.CLIENT_REDIRECT_URI_SPLITTERATOR + "uritwo";

  @Mock
  private Client mockClient;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(UUID.randomUUID().toString()).when(mockClient).getClientId();
    doReturn(UUID.randomUUID().toString()).when(mockClient).getSecret();
    doReturn(3600).when(mockClient).getAccessTokenValiditySeconds();
    doReturn(7200).when(mockClient).getRefreshTokenValiditySeconds();
    doReturn(CLIENT_REDIRECT_URIS).when(mockClient).getRedirectUri();
  }

  @Test
  public void clientDetailsImplShouldImplementClientDetails() {
    assertThat(ClientDetails.class).isAssignableFrom(ClientDetailsImpl.class);
  }

  @Test
  public void clientDetailsImplShouldHaveAllArgsConstructor() {
    Client client = new Client();

    assertThat(new ClientDetailsImpl(client)).hasFieldOrPropertyWithValue("client", client);
  }

  @Test
  public void clientDetailsImplShouldReturnInformationEquelToUser() {
    ClientDetailsImpl fixture = new ClientDetailsImpl(mockClient);

    assertThat(fixture.getClientId()).isEqualTo(mockClient.getClientId());
    assertThat(fixture.getResourceIds()).isEqualTo(new HashSet<>());
    assertThat(fixture.isSecretRequired()).isEqualTo(mockClient.getIsSecretRequired());
    assertThat(fixture.isAutoApprove("")).isEqualTo(mockClient.getIsAutoApprove());
    assertThat(fixture.getClientSecret()).isEqualTo(mockClient.getSecret());
    assertThat(fixture.getAccessTokenValiditySeconds())
      .isEqualTo(fixture.getAccessTokenValiditySeconds());
    assertThat(fixture.getRefreshTokenValiditySeconds())
      .isEqualTo(fixture.getRefreshTokenValiditySeconds());
    assertThat(fixture.isScoped()).isEqualTo(!mockClient.getScopes().isEmpty());
    assertThat(fixture.getRegisteredRedirectUri()).isEqualTo(new HashSet<>(Arrays
      .asList(CLIENT_REDIRECT_URIS.split(ClientDetailsImpl.CLIENT_REDIRECT_URI_SPLITTERATOR))));
    assertThat(fixture.getAuthorities()).isEqualTo(mockClient.getAuthorities().stream()
      .map(ClientAuthorityAssociation::getAuthority).collect(Collectors.toList()));
    assertThat(fixture.getAdditionalInformation()).isEqualTo(new HashMap<>());
  }
}
