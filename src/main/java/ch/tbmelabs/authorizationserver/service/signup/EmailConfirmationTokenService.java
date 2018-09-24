package ch.tbmelabs.authorizationserver.service.signup;

import ch.tbmelabs.authorizationserver.domain.User;
import ch.tbmelabs.authorizationserver.exception.EmailConfirmationTokenNotFoundException;

public interface EmailConfirmationTokenService {

  String createUniqueEmailConfirmationToken(User user);

  void confirmRegistration(String token) throws EmailConfirmationTokenNotFoundException;
}
