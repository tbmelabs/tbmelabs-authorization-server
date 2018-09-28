package ch.tbmelabs.authorizationserver.service.domain.impl;

import ch.tbmelabs.authorizationserver.domain.Authority;
import ch.tbmelabs.authorizationserver.domain.repository.AuthorityCRUDRepository;
import ch.tbmelabs.authorizationserver.service.domain.AuthorityService;
import org.springframework.stereotype.Service;

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
