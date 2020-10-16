package step;

import java.util.List;

public interface Reader<S> {

  List<S> read() throws Exception;

}
