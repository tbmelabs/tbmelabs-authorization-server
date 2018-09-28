package ch.tbmelabs.authorizationserver.domain.dto.mapper;

import ch.tbmelabs.authorizationserver.domain.Authority;
import ch.tbmelabs.authorizationserver.domain.dto.AuthorityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorityMapper extends EntityMapper<Authority, AuthorityDTO> {

}
