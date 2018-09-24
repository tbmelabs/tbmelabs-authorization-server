package ch.tbmelabs.authorizationserver.service.signup;

import ch.tbmelabs.authorizationserver.domain.User;
import ch.tbmelabs.authorizationserver.domain.dto.UserDTO;

public interface UserSignupService {

  boolean isUsernameUnique(UserDTO testUser);

  boolean doesUsernameMatchFormat(UserDTO testUser);

  boolean isEmailAddressUnique(UserDTO testUser);

  boolean isEmailAddress(UserDTO testUser);

  boolean doesPasswordMatchFormat(UserDTO testUser);

  boolean doPasswordsMatch(UserDTO testUser);

  UserDTO signUpNewUser(UserDTO newUserDTO);

  boolean isUserValid(UserDTO testUser);

  User sendConfirmationEmailIfEmailIsEnabled(User persistedUser);
}
