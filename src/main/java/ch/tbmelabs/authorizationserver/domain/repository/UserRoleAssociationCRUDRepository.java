package ch.tbmelabs.authorizationserver.domain.repository;

import ch.tbmelabs.authorizationserver.domain.User;
import ch.tbmelabs.authorizationserver.domain.association.userrole.UserRoleAssociation;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleAssociationCRUDRepository
  extends CrudRepository<UserRoleAssociation, Long> {

  Set<UserRoleAssociation> findAllByUser(User user);
}
