package ch.tbmelabs.authorizationserver.domain.repository;

import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ch.tbmelabs.authorizationserver.domain.Client;
import ch.tbmelabs.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;

@Repository
public interface ClientAuthorityAssociationCRUDRepository
  extends CrudRepository<ClientAuthorityAssociation, Long> {

  Set<ClientAuthorityAssociation> findAllByClient(Client client);
}
