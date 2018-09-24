package ch.tbmelabs.authorizationserver.service.mail;

import ch.tbmelabs.authorizationserver.domain.User;

public interface MailService {

  void sendMail(User receiver, String subject, String htmlMessage);
}
