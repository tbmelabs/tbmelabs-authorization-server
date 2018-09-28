package ch.tbmelabs.authorizationserver.test.web.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.authorizationserver.web.utils.LoggingExceptionTranslator;
import java.util.Collections;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

public class LoggingExceptionTranslatorTest {

  private static final String EXCEPTION_MESSAGE = "This is a test exception.";
  private static final IllegalArgumentException EXCEPTION =
    new IllegalArgumentException(EXCEPTION_MESSAGE);

  @Mock
  private ThrowableAnalyzer throwableAnalyzerFixture;

  @Mock
  private LoggingExceptionTranslator fixture;

  @Before
  public void beforeTestSetUp() throws Exception {
    initMocks(this);

    ReflectionTestUtils.setField(fixture, "throwableAnalyzer", throwableAnalyzerFixture);

    doCallRealMethod().when(fixture).translate(ArgumentMatchers.any(Exception.class));
    doReturn(new Throwable[]{}).when(throwableAnalyzerFixture)
      .determineCauseChain(ArgumentMatchers.any(Throwable.class));
  }

  @Test
  public void loggingExceptionTranslatorShouldBeAnnotated() {
    assertThat(LoggingExceptionTranslator.class).hasAnnotation(Component.class);
  }

  @Test
  public void loggingExceptionTranslatorShouldExtendDefaultWebResponseExceptionTranslator() {
    assertThat(DefaultWebResponseExceptionTranslator.class)
      .isAssignableFrom(LoggingExceptionTranslator.class);
  }

  @Test
  public void loggingExceptionTranslatorShouldTranslateExceptionToResponseEntity()
    throws Exception {
    ResponseEntity<OAuth2Exception> responseFixture = fixture.translate(EXCEPTION);

    assertThat(responseFixture.getBody()).hasCause(EXCEPTION)
      .hasStackTraceContaining(EXCEPTION_MESSAGE);
    assertThat(responseFixture.getStatusCodeValue()).isEqualTo(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    assertThat(responseFixture.getHeaders()).hasSize(2).containsExactly(
      entry("Cache-Control", Collections.singletonList("no-store")),
      entry("Pragma", Collections.singletonList("no-cache")));
  }
}
