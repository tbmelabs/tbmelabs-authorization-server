package ch.tbmelabs.authorizationserver.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ch.tbmelabs.authorizationserver.domain.association.userrole.UserRoleAssociation;

@Repository
public interface UserRoleAssociationRepository extends CrudRepository<UserRoleAssociation, Long> {

}
