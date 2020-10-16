package step.impl;

import com.google.common.collect.Lists;
import config.PropertyConfig;
import data.database.Column;
import data.database.Table;
import data.database.Database;
import step.Reader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class DatabaseMetadataReader implements Reader<Database> {

  @Override
  public List<Database> read() throws Exception {
    String url = PropertyConfig.getInstance().getString("database.url");
    String user = PropertyConfig.getInstance().getString("database.user");
    String password = PropertyConfig.getInstance().getString("database.password");
    String driverClassName = PropertyConfig.getInstance().getString("database.driver.class");
    String schema = PropertyConfig.getInstance().getString("database.schema");

    Class.forName(driverClassName);

    try (Connection connection = DriverManager.getConnection(url, user, password)) {
      DatabaseMetaData metaData = connection.getMetaData();

      List<Table> tables = Lists.newArrayList();

      try (ResultSet tableRs = metaData
          .getTables(connection.getCatalog(), "%", "%", new String[]{"TABLE", "VIEW"})) {

        while (tableRs.next()) {
          String tableName = tableRs.getString("TABLE_NAME");
          String tableComment = StringUtils.defaultString(tableRs.getString("REMARKS"));

          List<Column> columns = Lists.newArrayList();

          try (ResultSet columnRs = metaData
              .getColumns(connection.getCatalog(), schema, tableName, "%");
              ResultSet primaryKeyRs = metaData
                  .getPrimaryKeys(connection.getCatalog(), schema, tableName)) {

            List<String> primaryKeys = Lists.newArrayList();

            while (primaryKeyRs.next()) {
              primaryKeys.add(primaryKeyRs.getString("COLUMN_NAME"));
            }

            while (columnRs.next()) {
              columns.add(Column.builder()
                  .name(columnRs.getString("COLUMN_NAME"))
                  .description(StringUtils.defaultString(columnRs.getString("REMARKS")))
                  .typeCode(columnRs.getInt("DATA_TYPE"))
                  .typeName(columnRs.getString("TYPE_NAME"))
                  .nullable(StringUtils.equals(columnRs.getString("IS_NULLABLE"), "YES"))
                  .autoIncrement(StringUtils.equals(columnRs.getString("IS_AUTOINCREMENT"), "YES"))
                  .primaryKey(primaryKeys.contains(columnRs.getString("COLUMN_NAME")))
                  .build());
            }
          }

          tables.add(
              Table.builder().name(tableName).description(tableComment).columns(columns).build());
        }

      } finally {
        metaData.getConnection().close();
      }

      return Collections.singletonList(Database.builder().name(schema).tables(tables).build());
    }

  }

}
