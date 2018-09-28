package ch.tbmelabs.authorizationserver.domain;

import ch.tbmelabs.authorizationserver.domain.association.userrole.UserRoleAssociation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@Table(name = "users")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends AbstractAuditingEntity {

  @Transient
  public static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "pk_sequence",
    strategy = AbstractAuditingEntity.SEQUENCE_GENERATOR_STRATEGY,
    parameters = {@Parameter(name = "sequence_name", value = "users_id_seq"),
      @Parameter(name = "increment_size", value = "1")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Size(min = 5, max = 20)
  @Column(unique = true)
  private String username;

  @NotEmpty
  @Email
  @Size(max = 128)
  @Column(unique = true)
  private String email;

  @NotEmpty
  @Size(min = 60, max = 60)
  @Column(columnDefinition = "bpchar(60)")
  private String password;

  @Transient
  private String confirmation;

  @NotNull
  private Boolean isEnabled = false;

  @NotNull
  private Boolean isBlocked = false;

  @JsonBackReference
  @OneToOne(mappedBy = "user", cascade = {CascadeType.ALL})
  private EmailConfirmationToken emailConfirmationToken;

  @JsonManagedReference("user_has_roles")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
  private Set<UserRoleAssociation> roles = new HashSet<>();

  @PrePersist
  public void onCreate() {
    this.setPassword(PASSWORD_ENCODER.encode(this.getPassword()));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User other = (User) o;
    return id != null && Objects.equals(id, other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
