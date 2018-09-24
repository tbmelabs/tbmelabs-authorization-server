package ch.tbmelabs.authorizationserver.domain.dto.mapper;

import org.mapstruct.Mapper;
import ch.tbmelabs.authorizationserver.domain.GrantType;
import ch.tbmelabs.authorizationserver.domain.dto.GrantTypeDTO;

@Mapper(componentModel = "spring")
public interface GrantTypeMapper extends EntityMapper<GrantType, GrantTypeDTO> {

}
