package ch.tbmelabs.authorizationserver.domain.association.userrole;

import ch.tbmelabs.authorizationserver.domain.Role;
import ch.tbmelabs.authorizationserver.domain.User;
import java.io.Serializable;
import lombok.Data;

@Data
public class UserRoleAssociationId implements Serializable {

  private static final long serialVersionUID = 1L;

  private User user;
  private Role role;
}
