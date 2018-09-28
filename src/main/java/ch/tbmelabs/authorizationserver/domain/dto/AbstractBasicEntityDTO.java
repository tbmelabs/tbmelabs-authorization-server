package ch.tbmelabs.authorizationserver.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractBasicEntityDTO {

  private Long id;
  @JsonFormat(shape = JsonFormat.Shape.NUMBER)
  private Date created;
  @JsonFormat(shape = JsonFormat.Shape.NUMBER)
  private Date lastUpdated;
}
