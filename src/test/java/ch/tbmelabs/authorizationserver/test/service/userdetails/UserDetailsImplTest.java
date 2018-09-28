package ch.tbmelabs.authorizationserver.test.service.userdetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.authorizationserver.domain.User;
import ch.tbmelabs.authorizationserver.service.userdetails.UserDetailsImpl;
import java.util.ArrayList;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImplTest {

  @Mock
  private User mockUser;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(RandomStringUtils.random(11)).when(mockUser).getUsername();
    doReturn(RandomStringUtils.random(11)).when(mockUser).getEmail();
    doReturn(RandomStringUtils.random(11)).when(mockUser).getPassword();
    doReturn(true).when(mockUser).getIsEnabled();
    doReturn(false).when(mockUser).getIsBlocked();
  }

  @Test
  public void userDetailsImplShouldImplementUserDetails() {
    assertThat(UserDetails.class).isAssignableFrom(UserDetailsImpl.class);
  }

  @Test
  public void userDetailsServiceImplShouldHaveAllArgsConstructor() {
    User user = new User();

    assertThat(new UserDetailsImpl(user)).hasFieldOrPropertyWithValue("user", user);
  }

  @Test
  public void userDetailsImplShouldReturnInformationEqualToUser() {
    UserDetailsImpl fixture = new UserDetailsImpl(mockUser);

    assertThat(fixture.getUsername()).isEqualTo(mockUser.getUsername());
    assertThat(fixture.getPassword()).isEqualTo(mockUser.getPassword());
    assertThat(fixture.isEnabled()).isEqualTo(mockUser.getIsEnabled());
    assertThat(fixture.isAccountNonLocked()).isEqualTo(!mockUser.getIsBlocked());
    assertThat(fixture.isAccountNonExpired()).isTrue();
    assertThat(fixture.isCredentialsNonExpired()).isTrue();
    assertThat(fixture.getAuthorities()).isEqualTo(new ArrayList<>(mockUser.getRoles()));
  }
}
