package ch.tbmelabs.tv.core.entryserver.test.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.core.env.Environment;

import ch.tbmelabs.tv.core.entryserver.Application;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class ApplicationTest {
  private static final String PRODUCTIVE_AND_DEVELOPMENT_ENVIRONMENT_ACTIVE_ERROR_MESSAGE = "Do not attempt to run an application in productive and development environment at the same time!";

  @Mock
  private Environment environmentFixture;

  @Spy
  @InjectMocks
  private Application fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new String[] { SpringApplicationProfile.PROD, SpringApplicationProfile.DEV }).when(environmentFixture)
        .getActiveProfiles();

    doCallRealMethod().when(fixture).initBean();
  }

  @Test
  public void applicationShouldBeAnnotated() {
    assertThat(Application.class).hasAnnotation(SpringCloudApplication.class);
  }

  @Test
  public void applicationShouldHavePublicConstructor() {
    assertThat(new Application()).isNotNull();
  }

  @Test(expected = IllegalArgumentException.class)
  public void initBeanShouldThrowExceptionIfProductiveAndDevelopmentProfilesAreActive() {
    try {
      fixture.initBean();
    } catch (Exception e) {
      assertThat(e).isOfAnyClassIn(IllegalArgumentException.class);
      assertThat(e.getLocalizedMessage()).isEqualTo(PRODUCTIVE_AND_DEVELOPMENT_ENVIRONMENT_ACTIVE_ERROR_MESSAGE);
      throw e;
    }
  }
}