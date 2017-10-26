package ch.tbmelabs.tv.shared.resources.authentication.association.clientscope;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientScopeAssociationId implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long clientId;
  private Long clientScopeId;
}