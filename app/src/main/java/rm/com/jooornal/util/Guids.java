package rm.com.jooornal.util;

import android.support.annotation.NonNull;
import java.util.UUID;

/**
 * утилитарный класс для генерации уникальных ключей (GUID'ов)
 */
public final class Guids {

  private Guids() {
  }

  /**
   * возвращает уникальный случайный ключ
   *
   * @return строка с ключом
   */
  @NonNull public static String randomGuid() {
    return UUID.randomUUID().toString();
  }
}
