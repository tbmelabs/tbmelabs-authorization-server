package ch.tbmelabs.authorizationserver.service.domain;

import ch.tbmelabs.authorizationserver.domain.Scope;

public interface ScopeService {

  Scope findByName(String name);
}
