package ch.tbmelabs.authorizationserver.test.security.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.authorizationserver.domain.AuthenticationLog.AUTHENTICATION_STATE;
import ch.tbmelabs.authorizationserver.domain.User;
import ch.tbmelabs.authorizationserver.security.logging.AuthenticationAttemptLogger;
import ch.tbmelabs.authorizationserver.security.logging.AuthenticationSuccessHandler;
import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

public class AuthenticationSuccessHandlerTest {

  @Mock
  private AuthenticationAttemptLogger mockAuthenticationAttemptLogger;

  @Spy
  @InjectMocks
  private AuthenticationSuccessHandler fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);
  }

  @Test
  public void authenticationSuccessHandlerShouldBeAnnotated() {
    assertThat(AuthenticationSuccessHandler.class).hasAnnotation(Component.class);
  }

  @Test
  public void authenticationSuccessHandlerShouldExtendsSimpleUrlAuthenticationFailureHandler() {
    assertThat(SavedRequestAwareAuthenticationSuccessHandler.class)
      .isAssignableFrom(AuthenticationSuccessHandler.class);
  }

  @Test
  public void authenticationSuccessHandlerShouldTriggerLoggerAndBruteforceFilter()
    throws IOException, ServletException {
    MockHttpServletRequest mockRequest = new MockHttpServletRequest();
    mockRequest.setRemoteAddr("127.0.0.1");
    mockRequest.setParameter("username", "Testuser");

    fixture.onAuthenticationSuccess(mockRequest, new MockHttpServletResponse(),
      new UsernamePasswordAuthenticationToken(new User(),
        new UsernamePasswordCredentials("Testuser", "Testpassword")));

    verify(mockAuthenticationAttemptLogger, times(1)).logAuthenticationAttempt(
      ArgumentMatchers.eq(AUTHENTICATION_STATE.OK), ArgumentMatchers.eq("127.0.0.1"),
      ArgumentMatchers.isNull(),
      ArgumentMatchers.anyString());
  }
}
