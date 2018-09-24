package ch.tbmelabs.authorizationserver.service.domain.impl;

import org.springframework.stereotype.Service;
import ch.tbmelabs.authorizationserver.domain.Authority;
import ch.tbmelabs.authorizationserver.domain.repository.AuthorityCRUDRepository;
import ch.tbmelabs.authorizationserver.service.domain.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {

  private AuthorityCRUDRepository authorityRepository;

  public AuthorityServiceImpl(AuthorityCRUDRepository authorityRepository) {
    this.authorityRepository = authorityRepository;
  }

  @Override
  public Authority findByName(String name) {
    return authorityRepository.findByName(name)
      .orElse(authorityRepository.save(new Authority(name)));
  }
}
