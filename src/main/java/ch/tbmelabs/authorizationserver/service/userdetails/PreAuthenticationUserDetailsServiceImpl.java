package ch.tbmelabs.authorizationserver.service.userdetails;

import ch.tbmelabs.authorizationserver.domain.User;
import ch.tbmelabs.authorizationserver.domain.repository.UserCRUDRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class PreAuthenticationUserDetailsServiceImpl
  implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

  private static final Logger LOGGER =
    LoggerFactory.getLogger(PreAuthenticatedAuthenticationProviderImpl.class);

  private TokenStore tokenStore;

  private UserCRUDRepository userRepository;

  public PreAuthenticationUserDetailsServiceImpl(TokenStore tokenStore,
    UserCRUDRepository userCRUDRepository) {
    this.tokenStore = tokenStore;
    this.userRepository = userCRUDRepository;
  }

  @Override
  public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) {
    String username = tokenStore.readAuthentication((String) token.getPrincipal()).getName();

    LOGGER.debug("Loading userdetails for username \'{}\'", username);

    Optional<User> user;
    if (!(user = userRepository.findByUsernameIgnoreCase(username)).isPresent()) {
      throw new UsernameNotFoundException("Username \'" + username + "\' does not exist!");
    }

    return new UserDetailsImpl(user.get());
  }
}
