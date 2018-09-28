package ch.tbmelabs.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.authorizationserver.configuration.DatasourceConfiguration;
import ch.tbmelabs.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import javax.sql.DataSource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class DatasourceConfigurationIntTest
  extends AbstractOAuth2AuthorizationServerContextAwareTest {

  @Autowired
  private DatasourceConfiguration configuration;

  @Autowired
  private DataSource bean;

  @Autowired
  @Qualifier("jdbcTokenStoreDatasource")
  private DataSource secondaryBean;

  @Test
  public void primaryRegisteredBeanShouldEqualConfiguredDataSource() {
    assertThat(configuration.dataSource()).isEqualTo(bean);
  }

  @Test
  public void secondaryBeanShouldBeInitialized() {
    assertThat(configuration.jdbcTokenStoreDatasource()).isEqualTo(secondaryBean);
  }
}
