package ch.tbmelabs.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.authorizationserver.domain.GrantType;
import ch.tbmelabs.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

public class GrantTypeTest {

  private static final String TEST_GRANT_TYPE_NAME = RandomStringUtils.random(11);

  @Spy
  private GrantType fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);
  }

  @Test
  public void grantTypeShouldBeAnnotated() {
    assertThat(GrantType.class).hasAnnotation(Entity.class).hasAnnotation(Table.class);

    assertThat(GrantType.class.getDeclaredAnnotation(Table.class).name())
      .isEqualTo("client_grant_types");
  }

  @Test
  public void grantTypeShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(AbstractAuditingEntity.class).isAssignableFrom(GrantType.class);
  }

  @Test
  public void grantTypeShouldHaveNoArgsConstructor() {
    assertThat(new GrantType()).isNotNull();
  }

  @Test
  public void grantTypeShouldHaveAllArgsConstructor() {
    assertThat(new GrantType(TEST_GRANT_TYPE_NAME)).hasFieldOrPropertyWithValue("name",
      TEST_GRANT_TYPE_NAME);
  }

  @Test
  public void grantTypeShouldHaveIdGetterAndSetter() {
    Long id = new Random().nextLong();

    fixture.setId(id);

    assertThat(fixture).hasFieldOrPropertyWithValue("id", id);
    assertThat(fixture.getId()).isEqualTo(id);
  }

  @Test
  public void grantTypeShouldHaveNameGetterAndSetter() {
    String name = RandomStringUtils.random(11);

    fixture.setName(name);

    assertThat(fixture).hasFieldOrPropertyWithValue("name", name);
    assertThat(fixture.getName()).isEqualTo(name);
  }

  @Test
  public void grantTypeShouldHaveClientsGetterAndSetter() {
    Set<ClientGrantTypeAssociation> associations =
      new HashSet<>(Collections.singletonList(Mockito.mock(ClientGrantTypeAssociation.class)));

    fixture.setClientsWithGrantTypes(associations);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientsWithGrantTypes", associations);
    assertThat(fixture.getClientsWithGrantTypes()).isEqualTo(associations);
  }
}
