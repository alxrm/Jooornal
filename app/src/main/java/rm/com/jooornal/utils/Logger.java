package rm.com.jooornal.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * класс с утилитами для вывода информации в лог
 */
@SuppressWarnings("ALL") public final class Logger {
  private static final String LEVEL_DEBUG = "DEBUG";
  private static final String LEVEL_ERROR = "ERROR";

  private Logger() {
  }

  /**
   * метод, выводящий в лог значение любого объекта
   *
   * уровень сообщения в логе будет помечен как "Отладочный"
   *
   * @param msg объект, который нужно вывести в лог
   */
  public static void d(@Nullable Object msg) {
    d(String.valueOf(msg != null ? msg : "null"));
  }

  /**
   * метод, выводящий в лог переданную строку
   *
   * уровень сообщения в логе будет помечен как "Отладочный"
   *
   * @param msg строка, которую нужно вывести в лог
   */
  public static void d(@Nullable String msg) {
    Log.e(LEVEL_DEBUG, "" + msg);
  }

  /**
   * метод, выводящий в лог переданную строку
   *
   * уровень сообщения в логе будет помечен как "Ошибка"
   *
   * @param err сообщение ошибки, которое нужно вывести в лог
   */
  public static void e(@Nullable String err) {
    Log.e(LEVEL_ERROR, "" + err);
  }
  /**
   * метод, выводящий в лог название переданного объекта ошибки
   *
   * уровень сообщения в логе будет помечен как "Ошибка"
   *
   * @param err объект ошибки, который нужно вывести в лог
   */
  public static void e(@NonNull Throwable err) {
    e(err.getClass().getName());
  }
}
