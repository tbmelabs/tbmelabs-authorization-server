package ch.tbmelabs.authorizationserver.test.domain.association;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.authorizationserver.domain.Client;
import ch.tbmelabs.authorizationserver.domain.GrantType;
import ch.tbmelabs.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociationId;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ClientGrantTypeAssociationTest {

  @Mock
  private Client clientFixture;

  @Mock
  private GrantType grantTypeFixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new Random().nextLong()).when(clientFixture).getId();
    doReturn(new Random().nextLong()).when(grantTypeFixture).getId();
  }

  @Test
  public void clientGrantTypeAssociationShouldBeAnnotated() {
    assertThat(ClientGrantTypeAssociation.class).hasAnnotation(Entity.class)
      .hasAnnotation(Table.class);

    assertThat(ClientGrantTypeAssociation.class.getDeclaredAnnotation(Table.class).name())
      .isNotNull().isEqualTo("client_has_grant_types");
  }

  @Test
  public void clientGrantTypeAssociationShouldBeAnnotatedWithComposedIdClass() {
    assertThat(ClientGrantTypeAssociation.class).hasAnnotation(IdClass.class);
    assertThat(ClientGrantTypeAssociation.class.getDeclaredAnnotation(IdClass.class).value())
      .isNotNull().isEqualTo(ClientGrantTypeAssociationId.class);
  }

  @Test
  public void clientGrantTypeAssociationShouldHaveNoArgsConstructor() {
    assertThat(new ClientGrantTypeAssociation()).isNotNull();
  }

  @Test
  public void ShouldHaveAllArgsConstructor() {
    assertThat(new ClientGrantTypeAssociation(clientFixture, grantTypeFixture))
      .hasFieldOrPropertyWithValue("client", clientFixture)
      .hasFieldOrPropertyWithValue("grantType", grantTypeFixture);
  }

  @Test
  public void clientGrantTypeAssociationShouldHaveClientGetterAndSetter() {
    ClientGrantTypeAssociation fixture = new ClientGrantTypeAssociation();
    fixture.setClient(clientFixture);

    assertThat(fixture.getClient()).isEqualTo(clientFixture);
  }

  @Test
  public void clientGrantTypeAssociationShouldHaveGrantTypeGetterAndSetter() {
    ClientGrantTypeAssociation fixture = new ClientGrantTypeAssociation();
    fixture.setGrantType(grantTypeFixture);

    assertThat(fixture.getGrantType()).isEqualTo(grantTypeFixture);
  }
}
