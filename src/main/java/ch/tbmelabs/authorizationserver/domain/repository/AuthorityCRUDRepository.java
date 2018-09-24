package ch.tbmelabs.authorizationserver.domain.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ch.tbmelabs.authorizationserver.domain.Authority;

@Repository
public interface AuthorityCRUDRepository extends CrudRepository<Authority, Long> {

  Page<Authority> findAll(Pageable pageable);

  Optional<Authority> findByName(String name);
}
