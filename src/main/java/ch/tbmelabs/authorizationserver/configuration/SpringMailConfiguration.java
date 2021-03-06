package ch.tbmelabs.authorizationserver.configuration;

import ch.tbmelabs.serverconstants.spring.SpringApplicationProfileConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("!" + SpringApplicationProfileConstants.NO_MAIL)
@PropertySource(value = {"classpath:config/properties/mail.properties"})
public class SpringMailConfiguration {

}
