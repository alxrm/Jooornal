package rm.com.jooornal.utils;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex
 */

@SuppressWarnings("ALL") public final class Collections {
  private Collections() {
  }

  public static <T> List<T> filter(@NonNull List<T> target, @NonNull Predicate<T> clause) {
    final ArrayList<T> result = new ArrayList<>();

    for (T item : target) {
      if (clause.test(item)) {
        result.add(item);
      }
    }

    return result;
  }

  public static <T, R> List<R> map(@NonNull List<T> target,
      @NonNull Transformer<T, R> transformer) {
    final ArrayList<R> result = new ArrayList<>(target.size());

    for (T item : target) {
      result.add(transformer.invoke(item));
    }

    return result;
  }

  public interface Predicate<T> {
    boolean test(T item);
  }

  public interface Transformer<T, R> {
    R invoke(T item);
  }
}
