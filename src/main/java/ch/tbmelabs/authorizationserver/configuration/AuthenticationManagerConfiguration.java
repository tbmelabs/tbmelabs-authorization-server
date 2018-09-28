package ch.tbmelabs.authorizationserver.configuration;

import ch.tbmelabs.authorizationserver.domain.User;
import ch.tbmelabs.authorizationserver.service.userdetails.PreAuthenticatedAuthenticationProviderImpl;
import ch.tbmelabs.authorizationserver.service.userdetails.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
public class AuthenticationManagerConfiguration {

  private ObjectPostProcessor<Object> objectPostProcessor;

  private PreAuthenticatedAuthenticationProviderImpl preAuthenticationProvider;

  private UserDetailsServiceImpl userDetailsService;

  public AuthenticationManagerConfiguration(ObjectPostProcessor<Object> objectObjectPostProcessor,
    PreAuthenticatedAuthenticationProviderImpl preAuthenticatedAuthenticationProvider,
    UserDetailsServiceImpl userDetailsService) {
    this.objectPostProcessor = objectObjectPostProcessor;
    this.preAuthenticationProvider = preAuthenticatedAuthenticationProvider;
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(objectPostProcessor);
    builder.authenticationProvider(preAuthenticationProvider).userDetailsService(userDetailsService)
      .passwordEncoder(User.PASSWORD_ENCODER);
    return builder.build();
  }
}
