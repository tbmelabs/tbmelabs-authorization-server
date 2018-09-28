package ch.tbmelabs.authorizationserver.domain.repository;

import ch.tbmelabs.authorizationserver.domain.association.userrole.UserRoleAssociation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthorityAssociationCRUDRepository
  extends CrudRepository<UserRoleAssociation, Long> {

}
