package ch.tbmelabs.authorizationserver.domain.repository;

import ch.tbmelabs.authorizationserver.domain.AuthenticationLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationLogCRUDRepository extends CrudRepository<AuthenticationLog, Long> {

}
