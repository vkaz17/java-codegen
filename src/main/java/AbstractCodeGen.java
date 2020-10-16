import step.Processor;
import step.Reader;
import step.Writer;
import java.util.List;

public abstract class AbstractCodeGen<S, T> {

  public abstract Reader<S> reader();

  public abstract Processor<S, T> processor();

  public abstract Writer<T> writer();

  public void run() throws Exception {

    List<S> before = reader().read();

    List<T> after = processor().process(before);

    writer().write(after);

  }

}
