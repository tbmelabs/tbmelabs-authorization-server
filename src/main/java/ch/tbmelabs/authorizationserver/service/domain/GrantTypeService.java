package ch.tbmelabs.authorizationserver.service.domain;

import ch.tbmelabs.authorizationserver.domain.GrantType;

public interface GrantTypeService {

  GrantType findByName(String name);
}
