package ch.tbmelabs.authorizationserver.domain.association.clientauthority;

import java.io.Serializable;
import ch.tbmelabs.authorizationserver.domain.Authority;
import ch.tbmelabs.authorizationserver.domain.Client;
import lombok.Data;

@Data
public class ClientAuthorityAssociationId implements Serializable {

  private static final long serialVersionUID = 1L;

  private Client client;
  private Authority authority;
}
