package ch.tbmelabs.authorizationserver.domain.dto.mapper;

import ch.tbmelabs.authorizationserver.domain.Scope;
import ch.tbmelabs.authorizationserver.domain.dto.ScopeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScopeMapper extends EntityMapper<Scope, ScopeDTO> {

}
