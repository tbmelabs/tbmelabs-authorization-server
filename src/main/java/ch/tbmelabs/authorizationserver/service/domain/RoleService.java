package ch.tbmelabs.authorizationserver.service.domain;

import ch.tbmelabs.authorizationserver.domain.Role;

public interface RoleService {

  Role findByName(String name);
}
