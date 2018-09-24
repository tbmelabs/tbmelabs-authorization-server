package ch.tbmelabs.authorizationserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import ch.tbmelabs.authorizationserver.service.clientdetails.ClientDetailsServiceImpl;

@Configuration
public class TokenServiceConfiguration {

  private AuthenticationManager authenticationManager;

  private ClientDetailsServiceImpl clientDetailsService;

  private TokenStore tokenStore;

  public TokenServiceConfiguration(AuthenticationManager authenticationManager,
    ClientDetailsServiceImpl clientDetailsService, TokenStore tokenStore) {
    this.authenticationManager = authenticationManager;
    this.clientDetailsService = clientDetailsService;
    this.tokenStore = tokenStore;
  }

  @Bean
  public DefaultTokenServices tokenServiceBean() {
    DefaultTokenServices tokenService = new DefaultTokenServices();
    tokenService.setAuthenticationManager(authenticationManager);
    tokenService.setClientDetailsService(clientDetailsService);
    tokenService.setReuseRefreshToken(false);
    tokenService.setTokenStore(tokenStore);
    return tokenService;
  }
}
