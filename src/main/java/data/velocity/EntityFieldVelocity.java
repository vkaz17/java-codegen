package data.velocity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EntityFieldVelocity {

  private final String fieldName;

  private final String columnName;

  private final String description;

  private final String typeName;

  private final boolean nullable;

  private final boolean primaryKey;

  private final boolean autoIncrement;

}
