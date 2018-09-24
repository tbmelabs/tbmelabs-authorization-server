package ch.tbmelabs.authorizationserver.web.rest;

import ch.tbmelabs.authorizationserver.domain.dto.AuthorityDTO;
import ch.tbmelabs.authorizationserver.domain.dto.mapper.AuthorityMapper;
import ch.tbmelabs.authorizationserver.domain.repository.AuthorityCRUDRepository;
import ch.tbmelabs.serverconstants.security.UserRoleConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"${spring.data.rest.base-path}/authorities"})
@PreAuthorize("hasAuthority('" + UserRoleConstants.SERVER_ADMIN + "')")
public class AuthorityController {

  private AuthorityCRUDRepository authorityRepository;

  private AuthorityMapper authorityMapper;

  public AuthorityController(AuthorityCRUDRepository authorityCRUDRepository,
    AuthorityMapper authorityMapper) {
    this.authorityRepository = authorityCRUDRepository;
    this.authorityMapper = authorityMapper;
  }

  @GetMapping
  public Page<AuthorityDTO> getAllAuthorities(Pageable pageable) {
    return authorityRepository.findAll(pageable).map(authorityMapper::toDto);
  }
}
