package ch.tbmelabs.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.authorizationserver.configuration.DatasourceConfiguration;
import ch.tbmelabs.serverconstants.spring.SpringApplicationProfileEnum;
import com.zaxxer.hikari.HikariDataSource;
import java.lang.reflect.Method;
import org.junit.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

public class DatasourceConfigurationTest {

  private static final String PRIMARY_DATASOURCE_PREFIX = "spring.datasource";
  private static final String JDBC_TOKEN_STORE_DATASOURCE_PREFIX = "tokenstore.datasource";

  @Test
  public void dataSourceConfigurationShouldBeAnnotated() {
    assertThat(DatasourceConfiguration.class).hasAnnotation(Configuration.class);
  }

  @Test
  public void dataSourceConfigurationShouldHavePublicConstructor() {
    assertThat(new DatasourceConfiguration()).isNotNull();
  }

  @Test
  public void springDataSourceShouldBeAnnotatedAsPrimaryBean()
    throws NoSuchMethodException, SecurityException {
    assertThat(new DatasourceConfiguration().dataSource()).isInstanceOf(HikariDataSource.class);

    Method datasourceConfiguration = DatasourceConfiguration.class.getDeclaredMethod("dataSource");

    assertThat(datasourceConfiguration.getDeclaredAnnotation(Bean.class)).isNotNull();
    assertThat(datasourceConfiguration.getDeclaredAnnotation(Primary.class)).isNotNull();
    assertThat(datasourceConfiguration.getDeclaredAnnotation(ConfigurationProperties.class).value())
      .isEqualTo(PRIMARY_DATASOURCE_PREFIX);
  }

  @Test
  public void jdbcTokenStoreShouldBeAnnotatedNotToOccurInProductiveEnvironment()
    throws NoSuchMethodException, SecurityException {
    assertThat(new DatasourceConfiguration().jdbcTokenStoreDatasource())
      .isInstanceOf(HikariDataSource.class);

    Method jdbcTokenStoreDatasourceConfiguration =
      DatasourceConfiguration.class.getDeclaredMethod("jdbcTokenStoreDatasource");

    assertThat(jdbcTokenStoreDatasourceConfiguration.getDeclaredAnnotation(Bean.class)).isNotNull();
    assertThat(jdbcTokenStoreDatasourceConfiguration.getDeclaredAnnotation(Profile.class).value())
      .containsExactly(SpringApplicationProfileEnum.NO_REDIS.getName());
    assertThat(jdbcTokenStoreDatasourceConfiguration
      .getDeclaredAnnotation(ConfigurationProperties.class).value())
      .isEqualTo(JDBC_TOKEN_STORE_DATASOURCE_PREFIX);
  }
}
