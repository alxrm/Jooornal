package rm.com.jooornal.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.EMPTY_LIST;

/**
 * утилитарный класс для работы со списками
 */
@SuppressWarnings("ALL") public final class Lists {
  private Lists() {
  }

  /**
   * возвращает новый список, содержащий только те элементы, которые прошли тест в предикате
   *
   * @param target исходный список
   * @param clause предикат с проверкой
   * @param <T> тип элементов списка
   * @return отфильтрованный список
   */
  @NonNull public static <T> List<T> filter(@NonNull List<T> target, @NonNull Predicate<T> clause) {
    final ArrayList<T> result = new ArrayList<>();

    for (T item : target) {
      if (clause.test(item)) {
        result.add(item);
      }
    }

    return result;
  }

  /**
   * возвращает первый элемент, который проходит тест предиката
   *
   * @param target исходный список
   * @param clause предикат с проверкой
   * @param <T> тип элементов списка
   * @return первый элемент
   */
  @Nullable public static <T> T first(@NonNull List<T> target, @NonNull Predicate<T> clause) {
    for (T item : target) {
      if (clause.test(item)) {
        return item;
      }
    }

    return null;
  }

  /**
   * возвращает новый список, где каждый элемент преобразован
   *
   * @param target исходный список
   * @param transformer преобразователь, который вызывается на каждом элементе
   * @param <T> тип исходных элементов
   * @param <R> тип преобразованных элементов
   * @return преобразованный список
   */
  @NonNull public static <T, R> List<R> map(@NonNull List<T> target,
      @NonNull Transformer<T, R> transformer) {
    final ArrayList<R> result = new ArrayList<>(target.size());

    for (T item : target) {
      result.add(transformer.invoke(item));
    }

    return result;
  }

  /**
   * возвращает новое значение, в которое сжимаются все элементы списка
   *
   * @param target исходный список
   * @param initialValue изначальное вид возвращаемого значения
   * @param accumulator функция, которая добавляет каждый элемент к результирующему значению
   * @param <T> тип исходных элементов
   * @param <R> тип возвращаемого значения
   * @return сжатое значение
   */
  @NonNull public static <T, R> R reduce(@NonNull List<T> target, @NonNull R initialValue,
      @NonNull Accumulator<T, R> accumulator) {
    R result = initialValue;

    for (T item : target) {
      result = accumulator.collect(result, item);
    }

    return result;
  }

  /**
   * возвращает список из массива, если массив == null, то вернётся пустой список
   *
   * @param array изначальный массив или null
   * @param <T> тип элементов массива и списка
   * @return результиующий список
   */
  @NonNull public static <T> List<T> listOfArray(@Nullable T[] array) {
    if (array == null) {
      return EMPTY_LIST;
    }

    return Arrays.asList(array);
  }

  /**
   * предикат, принимает элемент и возвращает флаг, прошёл ли он тест
   *
   * @param <T> тип данных элемента
   */
  public interface Predicate<T> {
    boolean test(T item);
  }

  /**
   * трансформер, принимает элемент и возвращает преобразованный
   *
   * @param <T> тип исходного элемента
   * @param <R> тип результирующего элемента
   */
  public interface Transformer<T, R> {
    R invoke(T item);
  }

  /**
   * аккумулятор, добавляющий элемент к некоторому результату
   *
   * @param <T> тип добавляемого элемента
   * @param <R> тип результата
   */
  public interface Accumulator<T, R> {
    R collect(R result, T item);
  }
}
