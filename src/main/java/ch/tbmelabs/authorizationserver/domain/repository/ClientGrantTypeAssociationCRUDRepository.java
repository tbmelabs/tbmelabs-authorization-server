package ch.tbmelabs.authorizationserver.domain.repository;

import ch.tbmelabs.authorizationserver.domain.Client;
import ch.tbmelabs.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientGrantTypeAssociationCRUDRepository
  extends CrudRepository<ClientGrantTypeAssociation, Long> {

  Set<ClientGrantTypeAssociation> findAllByClient(Client client);
}
