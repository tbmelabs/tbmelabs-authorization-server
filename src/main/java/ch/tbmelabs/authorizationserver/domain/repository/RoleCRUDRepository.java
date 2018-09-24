package ch.tbmelabs.authorizationserver.domain.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ch.tbmelabs.authorizationserver.domain.Role;

@Repository
public interface RoleCRUDRepository extends CrudRepository<Role, Long> {

  Optional<Role> findByName(String name);
}
