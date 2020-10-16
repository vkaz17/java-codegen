package step;

import java.util.List;

public interface Processor<S, T> {

  List<T> process(List<S> list);

}
