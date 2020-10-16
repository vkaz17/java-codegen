package step;

import java.util.List;

public interface Writer<T> {

  void write(List<T> list) throws Exception;

}
