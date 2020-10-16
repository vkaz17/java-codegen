package data.database;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Database {

  private final String name;

  private final List<Table> tables;

}
