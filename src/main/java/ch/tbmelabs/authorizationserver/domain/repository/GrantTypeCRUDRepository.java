package ch.tbmelabs.authorizationserver.domain.repository;

import ch.tbmelabs.authorizationserver.domain.GrantType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrantTypeCRUDRepository extends CrudRepository<GrantType, Long> {

  Page<GrantType> findAll(Pageable pageable);

  Optional<GrantType> findByName(String name);
}
