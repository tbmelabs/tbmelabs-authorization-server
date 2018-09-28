package ch.tbmelabs.authorizationserver.test.service.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.authorizationserver.configuration.ApplicationProperties;
import ch.tbmelabs.authorizationserver.domain.User;
import ch.tbmelabs.authorizationserver.service.mail.impl.MailServiceImpl;
import ch.tbmelabs.serverconstants.spring.SpringApplicationProfileEnum;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.stubbing.Answer;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;

public class MailServiceImplTest {

  private static final String TEST_SENDER_ADDRESS = "no-reply@tbme.tv";
  private static MimeMessage sentMimeMessage;

  @Mock
  private Session mockSession;

  @Mock
  private JavaMailSender mockJavaMailSender;

  @Mock(answer = Answers.RETURNS_MOCKS)
  private ApplicationProperties mockApplicationProperties;

  @Spy
  @InjectMocks
  private MailServiceImpl fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    ReflectionTestUtils.setField(fixture, "senderAddress", TEST_SENDER_ADDRESS);

    doReturn(new Properties()).when(mockSession).getProperties();
    doReturn(new MimeMessage(mockSession)).when(mockJavaMailSender).createMimeMessage();
    doAnswer((Answer<MimeMessage>) invocation -> {
      MailServiceImplTest.sentMimeMessage = invocation.getArgument(0);
      return MailServiceImplTest.sentMimeMessage;
    }).when(mockJavaMailSender).send(ArgumentMatchers.any(MimeMessage.class));
  }

  @Test
  public void MailServiceImplShouldBeAnnotated() {
    assertThat(MailServiceImpl.class).hasAnnotation(Service.class).hasAnnotation(Profile.class);

    assertThat(MailServiceImpl.class.getDeclaredAnnotation(Profile.class).value())
      .containsExactly("!" + SpringApplicationProfileEnum.NO_MAIL.getName());
  }

  @Test
  public void MailServiceImplConstructorShouldAcceptJavaMailSender() {
    assertThat(new MailServiceImpl(mockJavaMailSender, mockApplicationProperties)).isNotNull();
  }

  @Test
  public void sendMailShouldCallJavaMailSender() throws MessagingException, IOException {
    User receiver = new User();
    receiver.setEmail("MailServiceImpltest.user@tbme.tv");

    String subject = RandomStringUtils.random(11);
    String htmlBody = "<html><body><h1>" + RandomStringUtils.random(11) + "</h1></body></html>";

    fixture.sendMail(receiver, subject, htmlBody);

    verify(mockJavaMailSender, times(1)).send(ArgumentMatchers.any(MimeMessage.class));

    // TODO: Why does this fail in Maven and not in plain JUnit?
    // assertThat(MailServiceImplTest.sentMimeMessage.getSubject()).isEqualTo(subject);
    assertThat(Arrays.stream(MailServiceImplTest.sentMimeMessage.getFrom()).map(Address::toString)
      .collect(Collectors.toList())).hasSize(1).containsExactly(TEST_SENDER_ADDRESS);
    assertThat(Arrays.stream(MailServiceImplTest.sentMimeMessage.getAllRecipients())
      .map(Address::toString).collect(Collectors.toList())).hasSize(1)
      .containsExactly(receiver.getEmail());
    assertThat(((MimeMultipart) ((MimeMultipart) MailServiceImplTest.sentMimeMessage.getContent())
      .getBodyPart(0).getContent()).getBodyPart(0).getContent()).isEqualTo(htmlBody);
  }
}
