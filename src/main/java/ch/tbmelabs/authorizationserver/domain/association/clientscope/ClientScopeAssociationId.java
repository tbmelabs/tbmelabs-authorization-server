package ch.tbmelabs.authorizationserver.domain.association.clientscope;

import ch.tbmelabs.authorizationserver.domain.Client;
import ch.tbmelabs.authorizationserver.domain.Scope;
import java.io.Serializable;
import lombok.Data;

@Data
public class ClientScopeAssociationId implements Serializable {

  private static final long serialVersionUID = 1L;

  private Client client;
  private Scope scope;
}
