package ch.tbmelabs.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.authorizationserver.domain.AuthenticationLog;
import ch.tbmelabs.authorizationserver.domain.AuthenticationLog.AUTHENTICATION_STATE;
import ch.tbmelabs.authorizationserver.domain.User;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;

public class AuthenticationLogTest {

  private static final AUTHENTICATION_STATE TEST_AUTHENTICATION_STATE = AUTHENTICATION_STATE.OK;
  private static final String TEST_IP = "127.0.0.1";
  private static final String TEST_MESSAGE = RandomStringUtils.random(11);

  @Mock
  private User mockUser;

  @Spy
  private AuthenticationLog fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);
  }

  @Test
  public void authenticationLogShouldBeAnnotated() {
    assertThat(AuthenticationLog.class).hasAnnotation(Entity.class).hasAnnotation(Table.class);

    assertThat(AuthenticationLog.class.getDeclaredAnnotation(Table.class).name()).isNotNull()
      .isEqualTo("authentication_log");
  }

  @Test
  public void authenticationLogShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(AbstractAuditingEntity.class).isAssignableFrom(AuthenticationLog.class);
  }

  @Test
  public void authenticationLogShouldHaveNoArgsConstructor() {
    assertThat(new AuthenticationLog()).isNotNull();
  }

  @Test
  public void authenticationLogShouldHaveAllArgsConstructor() {
    assertThat(new AuthenticationLog(TEST_AUTHENTICATION_STATE, TEST_IP, TEST_MESSAGE, mockUser))
      .hasFieldOrPropertyWithValue("state", TEST_AUTHENTICATION_STATE)
      .hasFieldOrPropertyWithValue("ip", TEST_IP)
      .hasFieldOrPropertyWithValue("message", TEST_MESSAGE)
      .hasFieldOrPropertyWithValue("user", mockUser);
  }

  @Test
  public void authenticationLogShouldHaveIdGetterAndSetter() {
    Long id = new Random().nextLong();

    fixture.setId(id);

    assertThat(fixture).hasFieldOrPropertyWithValue("id", id);
    assertThat(fixture.getId()).isEqualTo(id);
  }

  @Test
  public void authenticationLogShouldHaveStateGetterAndSetter() {
    AUTHENTICATION_STATE state = AUTHENTICATION_STATE.OK;

    fixture.setState(state);

    assertThat(fixture).hasFieldOrPropertyWithValue("state", state);
    assertThat(fixture.getState()).isEqualTo(state);
  }

  @Test
  public void authenticationLogShouldHaveIpGetterAndSetter() {
    String ip = "127.0.0.1";

    fixture.setIp(ip);

    assertThat(fixture).hasFieldOrPropertyWithValue("ip", ip);
    assertThat(fixture.getIp()).isEqualTo(ip);
  }

  @Test
  public void authenticationLogShouldHaveMessageGetterAndSetter() {
    String message = RandomStringUtils.random(11);

    fixture.setMessage(message);

    assertThat(fixture).hasFieldOrPropertyWithValue("message", message);
    assertThat(fixture.getMessage()).isEqualTo(message);
  }

  @Test
  public void authenticationLogShoudlHaveUserGetterAndSetter() {
    User user = new User();

    fixture.setUser(user);

    assertThat(fixture).hasFieldOrPropertyWithValue("user", user);
    assertThat(fixture.getUser()).isEqualTo(user);
  }
}
