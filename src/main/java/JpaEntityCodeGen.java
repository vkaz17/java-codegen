import data.database.Database;
import data.velocity.EntityVelocity;
import step.Processor;
import step.Reader;
import step.Writer;
import step.impl.DatabaseMetadataReader;
import step.impl.JpaEntityWriter;
import step.impl.EntityCodeGenProcessor;

public class JpaEntityCodeGen extends AbstractCodeGen<Database, EntityVelocity> {

  public static void main(String[] args) throws Exception {
    new JpaEntityCodeGen().run();
  }

  @Override
  public Reader<Database> reader() {
    return new DatabaseMetadataReader();
  }

  @Override
  public Processor<Database, EntityVelocity> processor() {
    return new EntityCodeGenProcessor();
  }

  @Override
  public Writer<EntityVelocity> writer() {
    return new JpaEntityWriter();
  }

}
