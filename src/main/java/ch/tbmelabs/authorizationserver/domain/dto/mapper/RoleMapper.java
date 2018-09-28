package ch.tbmelabs.authorizationserver.domain.dto.mapper;

import ch.tbmelabs.authorizationserver.domain.Role;
import ch.tbmelabs.authorizationserver.domain.dto.RoleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<Role, RoleDTO> {

}
