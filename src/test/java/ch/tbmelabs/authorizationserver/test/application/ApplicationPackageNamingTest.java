package ch.tbmelabs.authorizationserver.test.application;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import ch.tbmelabs.authorizationserver.Application;

public class ApplicationPackageNamingTest {

  private static final String APPLICATION_PACKAGE_NAME = "ch.tbmelabs.authorizationserver";

  @Test
  public void applicationPackageShouldMatchNamingConvention() {
    assertThat(Application.class.getPackage().getName()).isEqualTo(APPLICATION_PACKAGE_NAME);
  }
}
