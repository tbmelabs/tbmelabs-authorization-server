package ch.tbmelabs.authorizationserver.test;

import javax.transaction.Transactional;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import ch.tbmelabs.authorizationserver.Application;
import ch.tbmelabs.serverconstants.spring.SpringApplicationProfileConstants;

@Transactional
@AutoConfigureMockMvc
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({SpringApplicationProfileConstants.TEST, SpringApplicationProfileConstants.NO_REDIS,
    SpringApplicationProfileConstants.NO_MAIL})
@SpringBootTest(classes = {Application.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({ServletTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, DirtiesContextBeforeModesTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class, WithSecurityContextTestExecutionListener.class})
public abstract class AbstractOAuth2AuthorizationServerContextAwareTest {

  @Autowired
  private Flyway flyway;

  @Before
  public void beforeTestCleanMigrate() {
    flyway.clean();
    flyway.migrate();
  }
}
