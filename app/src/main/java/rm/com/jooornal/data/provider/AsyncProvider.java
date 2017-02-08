package rm.com.jooornal.data.provider;

import android.support.annotation.NonNull;

/**
 * асинхронный провайдер, выполняет какое-то действие асинхронно, после чего отправляет результат в
 * объект слушателя результата
 *
 * @param <T> тип результата асинхронной операции
 */
interface AsyncProvider<T> {

  /**
   * метод вызываемый для начала выполнения асинхронной операции
   *
   * @param callback объект слушателя результата
   */
  void provide(@NonNull ProviderListener<T> callback);
}
