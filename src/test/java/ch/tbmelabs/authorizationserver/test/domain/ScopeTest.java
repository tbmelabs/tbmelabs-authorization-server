package ch.tbmelabs.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.authorizationserver.domain.Scope;
import ch.tbmelabs.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
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

public class ScopeTest {

  private static final String TEST_SCOPE_NAME = RandomStringUtils.random(11);

  @Spy
  private Scope fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);
  }

  @Test
  public void scopeShouldBeAnnotated() {
    assertThat(Scope.class).hasAnnotation(Entity.class).hasAnnotation(Table.class);

    assertThat(Scope.class.getDeclaredAnnotation(Table.class).name()).isEqualTo("client_scopes");
  }

  @Test
  public void scopeShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(AbstractAuditingEntity.class).isAssignableFrom(Scope.class);
  }

  @Test
  public void scopeShouldHaveNoArgsConstructor() {
    assertThat(new Scope()).isNotNull();
  }

  @Test
  public void scopeShouldHaveAllArgsConstructor() {
    assertThat(new Scope(TEST_SCOPE_NAME)).hasFieldOrPropertyWithValue("name", TEST_SCOPE_NAME);
  }

  @Test
  public void scopeTypeShouldHaveIdGetterAndSetter() {
    Long id = new Random().nextLong();

    fixture.setId(id);

    assertThat(fixture).hasFieldOrPropertyWithValue("id", id);
    assertThat(fixture.getId()).isEqualTo(id);
  }

  @Test
  public void scopeShouldHaveNameGetterAndSetter() {
    String name = RandomStringUtils.random(11);

    fixture.setName(name);

    assertThat(fixture).hasFieldOrPropertyWithValue("name", name);
    assertThat(fixture.getName()).isEqualTo(name);
  }

  @Test
  public void grantTypeShouldHaveClientsGetterAndSetter() {
    Set<ClientScopeAssociation> associations =
      new HashSet<>(Collections.singletonList(Mockito.mock(ClientScopeAssociation.class)));

    fixture.setClientsWithScopes(associations);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientsWithScopes", associations);
    assertThat(fixture.getClientsWithScopes()).isEqualTo(associations);
  }
}
