package ch.tbmelabs.authorizationserver.test.domain.association;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.authorizationserver.domain.Role;
import ch.tbmelabs.authorizationserver.domain.User;
import ch.tbmelabs.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.authorizationserver.domain.association.userrole.UserRoleAssociationId;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class UserRoleAssociationTest {

  @Mock
  private User userFixture;

  @Mock
  private Role roleFixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new Random().nextLong()).when(userFixture).getId();
    doReturn(new Random().nextLong()).when(roleFixture).getId();
  }

  @Test
  public void userRoleAssociationShouldBeAnnotated() {
    assertThat(UserRoleAssociation.class).hasAnnotation(Entity.class).hasAnnotation(Table.class);

    assertThat(UserRoleAssociation.class.getDeclaredAnnotation(Table.class).name()).isNotNull()
      .isEqualTo("user_has_roles");
  }

  @Test
  public void userRoleAssociationShouldBeAnnotatedWithComposedIdClass() {
    assertThat(UserRoleAssociation.class).hasAnnotation(IdClass.class);
    assertThat(UserRoleAssociation.class.getDeclaredAnnotation(IdClass.class).value()).isNotNull()
      .isEqualTo(UserRoleAssociationId.class);
  }

  @Test
  public void userRoleAssociationShouldHaveNoArgsConstructor() {
    assertThat(new UserRoleAssociation()).isNotNull();
  }

  @Test
  public void userRoleAssociationShouldHaveAllArgsConstructor() {
    assertThat(new UserRoleAssociation(userFixture, roleFixture))
      .hasFieldOrPropertyWithValue("user", userFixture)
      .hasFieldOrPropertyWithValue("role", roleFixture);
  }

  @Test
  public void userRoleAssociationShouldHaveUserGetterAndSetter() {
    UserRoleAssociation fixture = new UserRoleAssociation();
    fixture.setUser(userFixture);

    assertThat(fixture.getUser()).isEqualTo(userFixture);
  }

  @Test
  public void userRoleAssociationShouldHaveRoleGetterAndSetter() {
    UserRoleAssociation fixture = new UserRoleAssociation();
    fixture.setRole(roleFixture);

    assertThat(fixture.getRole()).isEqualTo(roleFixture);
  }
}
