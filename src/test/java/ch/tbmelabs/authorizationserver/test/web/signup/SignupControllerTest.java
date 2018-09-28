package ch.tbmelabs.authorizationserver.test.web.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.authorizationserver.service.signup.UserSignupService;
import ch.tbmelabs.authorizationserver.web.signup.SignupController;
import java.lang.reflect.Method;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class SignupControllerTest {

  @Mock
  private UserSignupService userSignupServiceFixture;

  @Spy
  @InjectMocks
  private SignupController fixture;

  @Before
  public void beforeClassSetUp() {
    initMocks(this);

    doReturn(true).when(userSignupServiceFixture)
      .isUsernameUnique(ArgumentMatchers.any(UserDTO.class));
    doReturn(true).when(userSignupServiceFixture)
      .doesUsernameMatchFormat(ArgumentMatchers.any(UserDTO.class));
    doReturn(true).when(userSignupServiceFixture)
      .isEmailAddressUnique(ArgumentMatchers.any(UserDTO.class));
    doReturn(true).when(userSignupServiceFixture)
      .isEmailAddress(ArgumentMatchers.any(UserDTO.class));
    doReturn(true).when(userSignupServiceFixture)
      .doesPasswordMatchFormat(ArgumentMatchers.any(UserDTO.class));
    doReturn(true).when(userSignupServiceFixture)
      .doPasswordsMatch(ArgumentMatchers.any(UserDTO.class));
  }

  @Test
  public void signupControllerShouldBeAnnotated() {
    assertThat(SignupController.class).hasAnnotation(RestController.class)
      .hasAnnotation(RequestMapping.class);
    assertThat(SignupController.class.getDeclaredAnnotation(RequestMapping.class).value())
      .containsExactly("/signup");
  }

  @Test
  public void signupShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method signup =
      SignupController.class.getDeclaredMethod("signup", UserDTO.class);
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value())
      .containsExactly("/do-signup");
  }

  @Test
  public void signupShouldCallServiceMethod() {
    fixture.signup(new UserDTO());

    verify(userSignupServiceFixture, times(1)).signUpNewUser(ArgumentMatchers.any(UserDTO.class));
  }

  @Test
  public void isUsernameUniqueShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method signup = SignupController.class.getDeclaredMethod("isUsernameUnique",
      UserDTO.class);
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value())
      .containsExactly("/is-username-unique");
  }

  @Test
  public void isUsernameUniqueShouldCallServiceMethod() {
    fixture.isUsernameUnique(new UserDTO());

    verify(userSignupServiceFixture, times(1))
      .isUsernameUnique(ArgumentMatchers.any(UserDTO.class));
  }

  @Test
  public void doesUsernameMatchFormatShouldBeAnnotated()
    throws NoSuchMethodException, SecurityException {
    Method signup = SignupController.class.getDeclaredMethod("doesUsernameMatchFormat",
      UserDTO.class);
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value())
      .containsExactly("/does-username-match-format");
  }

  @Test
  public void doesUsernameMatchFormatShouldCallServiceMethod() {
    fixture.doesUsernameMatchFormat(new UserDTO());

    verify(userSignupServiceFixture, times(1))
      .doesUsernameMatchFormat(ArgumentMatchers.any(UserDTO.class));
  }

  @Test
  public void isEmailAddressUniqueShouldBeAnnotated()
    throws NoSuchMethodException, SecurityException {
    Method signup = SignupController.class.getDeclaredMethod("isEmailAddressUnique",
      UserDTO.class);
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value())
      .containsExactly("/is-email-unique");
  }

  @Test
  public void isEmailAddressUniqueShouldCallServiceMethod()
    throws SecurityException {
    fixture.isEmailAddressUnique(new UserDTO());

    verify(userSignupServiceFixture, times(1))
      .isEmailAddressUnique(ArgumentMatchers.any(UserDTO.class));
  }

  @Test
  public void isEmailAddressShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method signup =
      SignupController.class.getDeclaredMethod("isEmailAddress", UserDTO.class);
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value())
      .containsExactly("/is-email");
  }

  @Test
  public void isEmailAddressShouldCallServiceMethod() {
    fixture.isEmailAddress(new UserDTO());

    verify(userSignupServiceFixture, times(1)).isEmailAddress(ArgumentMatchers.any(UserDTO.class));
  }

  @Test
  public void doesPasswordMatchFormatShouldBeAnnotated()
    throws NoSuchMethodException, SecurityException {
    Method signup = SignupController.class.getDeclaredMethod("doesPasswordMatchFormat",
      UserDTO.class);
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value())
      .containsExactly("/does-password-match-format");
  }

  @Test
  public void doesPasswordMatchFormatShouldCallServiceMethod() {
    fixture.doesPasswordMatchFormat(new UserDTO());

    verify(userSignupServiceFixture, times(1))
      .doesPasswordMatchFormat(ArgumentMatchers.any(UserDTO.class));
  }

  @Test
  public void doPasswordsMatchShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method signup = SignupController.class.getDeclaredMethod("doPasswordsMatch",
      UserDTO.class);
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value())
      .containsExactly("/do-passwords-match");
  }

  @Test
  public void doPasswordsMatchShouldCallServiceMethod() {
    fixture.doPasswordsMatch(new UserDTO());

    verify(userSignupServiceFixture, times(1))
      .doPasswordsMatch(ArgumentMatchers.any(UserDTO.class));
  }
}
