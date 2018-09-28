package ch.tbmelabs.authorizationserver.domain.association.clientgranttype;

import ch.tbmelabs.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.authorizationserver.domain.Client;
import ch.tbmelabs.authorizationserver.domain.GrantType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_has_grant_types")
@IdClass(ClientGrantTypeAssociationId.class)
public class ClientGrantTypeAssociation extends AbstractAuditingEntity {

  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @JoinColumn(name = "client_id")
  @JsonBackReference("client_has_grant_types")
  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne(cascade = {CascadeType.ALL})
  private Client client;

  @Id
  @JoinColumn(name = "client_grant_type_id")
  @JsonBackReference("grant_type_has_clients")
  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
  private GrantType grantType;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ClientGrantTypeAssociation other = (ClientGrantTypeAssociation) o;
    return client != null && client.equals(other.client) && grantType != null
      && grantType.equals(other.grantType);
  }

  @Override
  public int hashCode() {
    if (client == null || client.getId() == null) {
      return Objects.hashCode(grantType.getId());
    } else if (grantType == null || grantType.getId() == null) {
      return Objects.hashCode(client.getId());
    }

    return Objects.hashCode(client.getId() + HASH_CODE_SEPARATOR + grantType.getId());
  }
}
