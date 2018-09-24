package ch.tbmelabs.authorizationserver.domain.dto.mapper;

import org.mapstruct.Mapper;
import ch.tbmelabs.authorizationserver.domain.Authority;
import ch.tbmelabs.authorizationserver.domain.dto.AuthorityDTO;

@Mapper(componentModel = "spring")
public interface AuthorityMapper extends EntityMapper<Authority, AuthorityDTO> {

}
