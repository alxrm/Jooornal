package rm.com.jooornal.data.provider;

import android.support.annotation.NonNull;

/**
 * интерфейс слушателя результатов чтения JSON файла
 *
 * @param <T> тип данных результата
 */
public interface ProviderListener<T> {

  /**
   * метод, который должен вызываться при передаче результата
   *
   * @param payload сам результат, экземпляр типа данных результата
   */
  void onProviderResult(@NonNull T payload);
}
