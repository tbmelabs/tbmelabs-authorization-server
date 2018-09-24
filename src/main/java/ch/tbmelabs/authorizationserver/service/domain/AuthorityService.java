package ch.tbmelabs.authorizationserver.service.domain;

import ch.tbmelabs.authorizationserver.domain.Authority;

public interface AuthorityService {

  Authority findByName(String name);
}
