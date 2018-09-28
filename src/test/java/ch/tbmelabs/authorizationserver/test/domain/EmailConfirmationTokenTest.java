package ch.tbmelabs.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.authorizationserver.domain.EmailConfirmationToken;
import ch.tbmelabs.authorizationserver.domain.User;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

public class EmailConfirmationTokenTest {

  @Spy
  private EmailConfirmationToken fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);
  }

  @Test
  public void emailConfirmationTokenShouldBeAnnotated() {
    assertThat(EmailConfirmationToken.class).hasAnnotation(Entity.class).hasAnnotation(Table.class);

    assertThat(EmailConfirmationToken.class.getDeclaredAnnotation(Table.class).name()).isNotNull()
      .isEqualTo("email_confirmation_tokens");
  }

  @Test
  public void emailConfirmationTokenShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(AbstractAuditingEntity.class).isAssignableFrom(EmailConfirmationToken.class);
  }

  @Test
  public void emailConfirmationTokenShouldHaveNoArgsConstructor() {
    assertThat(new EmailConfirmationToken()).isNotNull();
  }

  @Test
  public void emailConfirmationTokenShouldHaveAllArgsConstructor() {
    String tokenString = RandomStringUtils.random(60);
    User user = new User();
    Calendar expected = Calendar.getInstance();
    expected.add(Calendar.DATE, 1);

    fixture = new EmailConfirmationToken(tokenString, user);

    Calendar given = Calendar.getInstance();
    given.setTime(fixture.getExpirationDate());

    assertThat(fixture).hasFieldOrPropertyWithValue("tokenString", tokenString)
      .hasFieldOrPropertyWithValue("user", user);
    assertThat(given.get(Calendar.DATE)).isEqualTo(expected.get(Calendar.DATE));
  }

  @Test
  public void emailConfirmationTokenShouldHaveIdGetterAndSetter() {
    EmailConfirmationToken emailConfirmationToken =
      Mockito.mock(EmailConfirmationToken.class, Mockito.CALLS_REAL_METHODS);
    Long id = new Random().nextLong();

    emailConfirmationToken.setId(id);

    assertThat(emailConfirmationToken).hasFieldOrPropertyWithValue("id", id);
    assertThat(emailConfirmationToken.getId()).isEqualTo(id);
  }

  @Test
  public void emailConfirmationTokenShouldHaveTokenStringGetterAndSetter() {
    String tokenString = RandomStringUtils.random(60);

    fixture.setTokenString(tokenString);

    assertThat(fixture).hasFieldOrPropertyWithValue("tokenString", tokenString);
    assertThat(fixture.getTokenString()).isEqualTo(tokenString);
  }

  @Test
  public void emailConfirmationTokenShouldHaveExpirationDateGetterAndSetter() {
    Date expirationDate = new Date();

    fixture.setExpirationDate(expirationDate);

    assertThat(fixture).hasFieldOrPropertyWithValue("expirationDate", expirationDate);
    assertThat(fixture.getExpirationDate()).isEqualTo(expirationDate);
  }

  @Test
  public void emailConfirmationTokenShouldHaveUserGetterAndSetter() {
    User user = new User();

    fixture.setUser(user);

    assertThat(fixture).hasFieldOrPropertyWithValue("user", user);
    assertThat(fixture.getUser()).isEqualTo(user);
  }
}
