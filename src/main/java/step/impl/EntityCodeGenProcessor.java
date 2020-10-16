package step.impl;

import data.database.ColumnType;
import data.database.Database;
import data.velocity.EntityFieldVelocity;
import data.velocity.EntityVelocity;
import step.Processor;
import com.google.common.base.CaseFormat;
import java.util.List;
import java.util.stream.Collectors;

public class EntityCodeGenProcessor implements Processor<Database, EntityVelocity> {

  @Override
  public List<EntityVelocity> process(List<Database> databases) {

    Database database = databases.get(0);

    return database.getTables().stream().map(table -> {
      List<EntityFieldVelocity> entityFieldVelocities = table.getColumns().stream()
          .map(column -> EntityFieldVelocity.builder()
              .fieldName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, column.getName()))
              .columnName(column.getName())
              .description(column.getDescription())
              .typeName(ColumnType.of(column.getTypeName()).getJavaTypeClass().getSimpleName())
              .nullable(column.isNullable())
              .primaryKey(column.isPrimaryKey())
              .autoIncrement(column.isAutoIncrement())
              .build()).collect(Collectors.toList());

      return EntityVelocity.builder()
          .tableName(table.getName())
          .className(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName()))
          .entityFieldVelocities(entityFieldVelocities).build();
    }).collect(Collectors.toList());

  }

}
