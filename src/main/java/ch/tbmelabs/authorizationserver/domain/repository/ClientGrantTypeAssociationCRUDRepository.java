package ch.tbmelabs.authorizationserver.domain.repository;

import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ch.tbmelabs.authorizationserver.domain.Client;
import ch.tbmelabs.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;

@Repository
public interface ClientGrantTypeAssociationCRUDRepository
  extends CrudRepository<ClientGrantTypeAssociation, Long> {

  Set<ClientGrantTypeAssociation> findAllByClient(Client client);
}
