package data.velocity;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EntityVelocity {

  private final String tableName;

  private final String className;

  private final List<EntityFieldVelocity> entityFieldVelocities;

}
