package ch.tbmelabs.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.authorizationserver.configuration.TokenStoreConfiguration;
import ch.tbmelabs.authorizationserver.configuration.TokenStoreConfiguration.JdbcTokenStoreConfiguration;
import ch.tbmelabs.authorizationserver.configuration.TokenStoreConfiguration.RedisTokenStoreConfiguration;
import ch.tbmelabs.serverconstants.spring.SpringApplicationProfileEnum;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.test.util.ReflectionTestUtils;

public class TokenStoreConfigurationTest {

  @Mock
  private RedisConnectionFactory mockRedisConnectionFactory;

  @Mock
  private ApplicationContext mockApplicationContext;

  @Mock
  private RedisTokenStoreConfiguration redisFixture;

  @Mock
  private JdbcTokenStoreConfiguration jdbcFixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    ReflectionTestUtils.setField(redisFixture, "redisConnectionFactory",
      mockRedisConnectionFactory);
    ReflectionTestUtils.setField(jdbcFixture, "applicationContext", mockApplicationContext);

    doReturn(Mockito.mock(DataSource.class)).when(mockApplicationContext)
      .getBean(JdbcTokenStoreConfiguration.JDBC_TOKENSTORE_DATASOURCE_BEAN_NAME);

    doCallRealMethod().when(redisFixture).tokenStore();
    doCallRealMethod().when(jdbcFixture).tokenStore();
  }

  @Test
  public void tokenStoreConfigurationShouldBeAnnotated() {
    assertThat(TokenStoreConfiguration.class).hasAnnotation(Configuration.class);
  }

  @Test
  public void tokenStoreConfigurationShouldHavePublicConstructor() {
    assertThat(new TokenStoreConfiguration()).isNotNull();
  }

  @Test
  public void redisTokenStoreConfigurationShouldBeAnnotated() {
    assertThat(RedisTokenStoreConfiguration.class).hasAnnotation(Configuration.class)
      .hasAnnotation(Profile.class);
  }

  @Test
  public void redisTokenStoreShouldReturnRedisTokenStore() {
    assertThat(redisFixture.tokenStore()).isNotNull().isInstanceOf(RedisTokenStore.class);
  }

  @Test
  public void jdbcTokenStoreConfigurationShouldBeAnnotated() {
    assertThat(JdbcTokenStoreConfiguration.class).hasAnnotation(Configuration.class)
      .hasAnnotation(Profile.class);

    assertThat(JdbcTokenStoreConfiguration.class.getDeclaredAnnotation(Profile.class).value())
      .hasSize(1).containsExactly(SpringApplicationProfileEnum.NO_REDIS.getName());
  }

  @Test
  public void jdbcTokenStoreShouldReturnJdbcTokenStore() {
    assertThat(jdbcFixture.tokenStore()).isNotNull().isInstanceOf(JdbcTokenStore.class);
  }
}
