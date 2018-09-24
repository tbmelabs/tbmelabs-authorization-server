package ch.tbmelabs.authorizationserver.domain.association.clientgranttype;

import java.io.Serializable;
import ch.tbmelabs.authorizationserver.domain.Client;
import ch.tbmelabs.authorizationserver.domain.GrantType;
import lombok.Data;

@Data
public class ClientGrantTypeAssociationId implements Serializable {

  private static final long serialVersionUID = 1L;

  private Client client;
  private GrantType grantType;
}
