package data.database;

import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ColumnType {

  VARCHAR("VARCHAR", String.class),
  BIGINT("BIGINT", Long.class),
  INT("INT", Integer.class),
  BIT("BIT", Boolean.class),
  TIMESTAMP("TIMESTAMP", LocalDateTime.class),
  DATETIME("DATETIME", LocalDateTime.class);

  private final String typeName;

  private final Class<?> javaTypeClass;

  public static ColumnType of(String typeName) {
    return Arrays.stream(ColumnType.values())
        .filter(e -> StringUtils.startsWith(typeName, e.typeName))
        .findFirst()
        .orElseThrow(() -> new RuntimeException(typeName + " not defined. please define manually"));
  }

}
