package ch.tbmelabs.authorizationserver.domain.dto.mapper;

import org.mapstruct.Mapper;
import ch.tbmelabs.authorizationserver.domain.Role;
import ch.tbmelabs.authorizationserver.domain.dto.RoleDTO;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<Role, RoleDTO> {

}
