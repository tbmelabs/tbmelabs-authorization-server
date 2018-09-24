package ch.tbmelabs.authorizationserver.domain.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ch.tbmelabs.authorizationserver.domain.EmailConfirmationToken;

@Repository
public interface EmailConfirmationTokenCRUDRepository
  extends CrudRepository<EmailConfirmationToken, Long> {

  Optional<EmailConfirmationToken> findByTokenString(String tokenString);
}
