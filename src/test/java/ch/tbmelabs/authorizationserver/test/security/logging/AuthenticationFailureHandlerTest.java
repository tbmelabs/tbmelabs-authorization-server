package ch.tbmelabs.authorizationserver.test.security.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.authorizationserver.domain.AuthenticationLog.AUTHENTICATION_STATE;
import ch.tbmelabs.authorizationserver.security.logging.AuthenticationAttemptLogger;
import ch.tbmelabs.authorizationserver.security.logging.AuthenticationFailureHandler;
import java.io.IOException;
import javax.servlet.ServletException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

public class AuthenticationFailureHandlerTest {

  private static final String AUTHENTICATION_FAILED_ERROR_MESSAGE = "Test-Authentication failed!";

  @Mock
  private AuthenticationAttemptLogger mockAuthenticationAttemptLogger;

  @Spy
  @InjectMocks
  private AuthenticationFailureHandler fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);
  }

  @Test
  public void authenticationFailureHandlerShouldBeAnnotated() {
    assertThat(AuthenticationFailureHandler.class).hasAnnotation(Component.class);
  }

  @Test
  public void authenticationFailureHandlerShouldExtendsSimpleUrlAuthenticationFailureHandler() {
    assertThat(SimpleUrlAuthenticationFailureHandler.class)
      .isAssignableFrom(AuthenticationFailureHandler.class);
  }

  @Test
  public void authenticationFailureHandlerShouldTriggerLoggerAndBruteforceFilter()
    throws IOException, ServletException {
    MockHttpServletRequest mockRequest = new MockHttpServletRequest();
    mockRequest.setRemoteAddr("127.0.0.1");
    mockRequest.setParameter("username", "Testuser");

    fixture.onAuthenticationFailure(mockRequest, new MockHttpServletResponse(),
      new UsernameNotFoundException(AUTHENTICATION_FAILED_ERROR_MESSAGE));

    verify(mockAuthenticationAttemptLogger, times(1)).logAuthenticationAttempt(
      ArgumentMatchers.eq(AUTHENTICATION_STATE.NOK), ArgumentMatchers.eq("127.0.0.1"),
      ArgumentMatchers.eq(AUTHENTICATION_FAILED_ERROR_MESSAGE), ArgumentMatchers.anyString());
  }
}
