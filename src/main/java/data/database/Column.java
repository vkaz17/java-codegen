package data.database;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Column {

  private final String name;

  private final String description;

  private final int typeCode;

  private final String typeName;

  private final boolean nullable;

  private final boolean primaryKey;

  private final boolean autoIncrement;

}
