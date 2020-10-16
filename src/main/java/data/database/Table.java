package data.database;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Table {

  private final String name;

  private final String description;

  private final List<Column> columns;

}
