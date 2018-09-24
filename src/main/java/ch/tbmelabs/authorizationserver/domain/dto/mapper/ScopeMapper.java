package ch.tbmelabs.authorizationserver.domain.dto.mapper;

import org.mapstruct.Mapper;
import ch.tbmelabs.authorizationserver.domain.Scope;
import ch.tbmelabs.authorizationserver.domain.dto.ScopeDTO;

@Mapper(componentModel = "spring")
public interface ScopeMapper extends EntityMapper<Scope, ScopeDTO> {

}
