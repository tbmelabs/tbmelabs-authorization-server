package ch.tbmelabs.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.authorizationserver.domain.AbstractAuditingEntity;
import java.io.Serializable;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

public class AbstractAuditingEntityTest {

  @Spy
  private AbstractAuditingEntity fixture;

  @Before
  public void beforeClassSetUp() {
    initMocks(this);
  }

  @Test
  public void nicelyDocumentedJDBCResourceShouldBeAnnotated() {
    assertThat(AbstractAuditingEntity.class).hasAnnotation(MappedSuperclass.class)
      .hasAnnotation(EntityListeners.class);

    assertThat(AbstractAuditingEntity.class.getDeclaredAnnotation(EntityListeners.class).value())
      .containsExactly(AuditingEntityListener.class);
  }

  @Test
  public void nicelyDocumentedJDBCResourceShouldExtendSerializable() {
    assertThat(Serializable.class).isAssignableFrom(AbstractAuditingEntity.class);
  }
}
