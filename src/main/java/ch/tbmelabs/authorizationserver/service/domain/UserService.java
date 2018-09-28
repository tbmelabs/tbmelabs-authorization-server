package ch.tbmelabs.authorizationserver.service.domain;

import ch.tbmelabs.authorizationserver.domain.User;
import ch.tbmelabs.authorizationserver.domain.dto.UserDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  User save(UserDTO userDTO);

  Page<UserDTO> findAll(Pageable pageable);

  Optional<User> findById(Long id);

  User update(UserDTO userDTO);

  void delete(Long id);
}
