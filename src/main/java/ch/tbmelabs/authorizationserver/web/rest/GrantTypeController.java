package ch.tbmelabs.authorizationserver.web.rest;

import ch.tbmelabs.authorizationserver.domain.dto.GrantTypeDTO;
import ch.tbmelabs.authorizationserver.domain.dto.mapper.GrantTypeMapper;
import ch.tbmelabs.authorizationserver.domain.repository.GrantTypeCRUDRepository;
import ch.tbmelabs.serverconstants.security.UserRoleConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"${spring.data.rest.base-path}/grant-types"})
@PreAuthorize("hasAuthority('" + UserRoleConstants.SERVER_ADMIN + "')")
public class GrantTypeController {

  private GrantTypeCRUDRepository grantTypeRepository;

  private GrantTypeMapper grantTypeMapper;

  public GrantTypeController(GrantTypeCRUDRepository grantTypeCRUDRepository,
    GrantTypeMapper grantTypeMapper) {
    this.grantTypeRepository = grantTypeCRUDRepository;
    this.grantTypeMapper = grantTypeMapper;
  }

  @GetMapping
  public Page<GrantTypeDTO> getAllGrantTypes(Pageable pageable) {
    return grantTypeRepository.findAll(pageable).map(grantTypeMapper::toDto);
  }
}
