package rm.com.jooornal.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * класс с утилитами для проверки условий
 */
public final class Conditions {

  private Conditions() {
  }

  /**
   * метод проверяет условие, если оно не выполняется, то выбрасывается исключение
   *
   * @param clause само условие
   */
  public static void check(boolean clause) {
    if (!clause) throw new IllegalStateException("Check failed");
  }

  /**
   * метод проверяет условие, если оно не выполняется, то выбрасывается исключение
   *
   * @param clause само условие
   * @param message дополнительное сообщение, которое можно вывести в лог
   * после завершения работы приложения
   */
  public static void check(boolean clause, @NonNull String message) {
    if (!clause) throw new IllegalStateException("Check failed: " + message);
  }

  /**
   * метод проверяет, является ли ссылка {@code null}, если является, то выбрасывается исключение
   *
   * @param reference ссылка, которую нужно проверить
   * @param <T> тип данных ссылки
   * @return для удобства, в случае успешной проверки, здесь возвращается та же ссылка
   */
  public static <T> T checkNotNull(@Nullable T reference) {
    if (reference == null) {
      throw new NullPointerException("Element should not be null");
    }

    return reference;
  }

  /**
   * метод проверяет, является ли ссылка {@code null}, если является, то выбрасывается исключение
   *
   * @param reference ссылка, которую нужно проверить
   * @param <T> тип данных ссылки
   * @param message дополнительное сообщение, которое можно вывести в лог
   * после завершения работы приложения
   * @return для удобства, в случае успешной проверки, здесь возвращается та же ссылка
   */
  public static <T> T checkNotNull(@Nullable T reference, @NonNull String message) {
    if (reference == null) {
      throw new NullPointerException("Element should not be null: " + message);
    }

    return reference;
  }
}
