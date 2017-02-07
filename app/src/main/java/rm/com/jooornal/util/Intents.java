package rm.com.jooornal.util;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * утилитарный класс для работы с интентами
 */
public final class Intents {
  private Intents() {
  }

  /**
   * проверяет интент на валидность
   *
   * @param intent интент, который нужно проверить
   * @param action действие, которое он должен в себе хранить
   * @return возвращает флаг, является ли интент проверенным
   */
  public static boolean checkIntent(@Nullable Intent intent, @NonNull String action) {
    return intent != null
        && action.equalsIgnoreCase(intent.getAction())
        && intent.getExtras() != null;
  }
}
