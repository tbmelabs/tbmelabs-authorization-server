package ch.tbmelabs.authorizationserver.test.domain.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.authorizationserver.domain.Client;
import ch.tbmelabs.authorizationserver.domain.dto.ClientDTO;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class ClientDTOTest {

  public static Client createTestClient() {
    Client testClient = new Client();
    testClient.setClientId(RandomStringUtils.random(36));
    testClient.setSecret(RandomStringUtils.random(36));
    testClient.setAccessTokenValiditySeconds(new Random().nextInt());
    testClient.setRefreshTokenValiditySeconds(new Random().nextInt());
    testClient.setRedirectUri("https://tbme.tv");

    return testClient;
  }

  @Test
  public void clientDTOShouldHavePublicConstructor() {
    assertThat(new ClientDTO()).isNotNull();
  }
}
