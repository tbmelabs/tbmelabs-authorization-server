package ch.tbmelabs.authorizationserver.service.domain.impl;

import ch.tbmelabs.authorizationserver.domain.Scope;
import ch.tbmelabs.authorizationserver.domain.repository.ScopeCRUDRepository;
import ch.tbmelabs.authorizationserver.service.domain.ScopeService;
import org.springframework.stereotype.Service;

@Service
public class ScopeServiceImpl implements ScopeService {

  private ScopeCRUDRepository scopeRepository;

  public ScopeServiceImpl(ScopeCRUDRepository scopeRepository) {
    this.scopeRepository = scopeRepository;
  }

  @Override
  public Scope findByName(String name) {
    return scopeRepository.findByName(name).orElse(scopeRepository.save(new Scope(name)));
  }
}
