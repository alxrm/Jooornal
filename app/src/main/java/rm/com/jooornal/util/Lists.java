package rm.com.jooornal.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.EMPTY_LIST;

/**
 * Created by alex
 */

@SuppressWarnings("ALL") public final class Lists {
  private Lists() {
  }

  @NonNull public static <T> List<T> filter(@NonNull List<T> target, @NonNull Predicate<T> clause) {
    final ArrayList<T> result = new ArrayList<>();

    for (T item : target) {
      if (clause.test(item)) {
        result.add(item);
      }
    }

    return result;
  }

  @Nullable public static <T> T first(@NonNull List<T> target, @NonNull Predicate<T> clause) {
    for (T item : target) {
      if (clause.test(item)) {
        return item;
      }
    }

    return null;
  }

  @NonNull public static <T, R> List<R> map(@NonNull List<T> target,
      @NonNull Transformer<T, R> transformer) {
    final ArrayList<R> result = new ArrayList<>(target.size());

    for (T item : target) {
      result.add(transformer.invoke(item));
    }

    return result;
  }

  @NonNull public static <T, R> R reduce(@NonNull List<T> target, @NonNull R initialValue,
      @NonNull Accumulator<T, R> accumulator) {
    R result = initialValue;

    for (T item : target) {
      result = accumulator.collect(result, item);
    }

    return result;
  }

  @NonNull public static <T> List<T> listOfArray(@Nullable T[] array) {
    if (array == null) {
      return EMPTY_LIST;
    }

    return Arrays.asList(array);
  }

  public interface Predicate<T> {
    boolean test(T item);
  }

  public interface Transformer<T, R> {
    R invoke(T item);
  }

  public interface Accumulator<T, R> {
    R collect(R result, T item);
  }
}
