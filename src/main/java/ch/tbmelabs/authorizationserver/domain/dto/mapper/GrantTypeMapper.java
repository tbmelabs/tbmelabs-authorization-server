package ch.tbmelabs.authorizationserver.domain.dto.mapper;

import ch.tbmelabs.authorizationserver.domain.GrantType;
import ch.tbmelabs.authorizationserver.domain.dto.GrantTypeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GrantTypeMapper extends EntityMapper<GrantType, GrantTypeDTO> {

}
