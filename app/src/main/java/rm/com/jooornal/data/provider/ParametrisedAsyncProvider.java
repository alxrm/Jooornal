package rm.com.jooornal.data.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.concurrent.ExecutorService;

/**
 * абстрактный класс провайдера с параметром
 *
 * @param <P> тип параметра
 * @param <T> тип результата
 */
public abstract class ParametrisedAsyncProvider<P, T> extends AbstractAsyncProvider<T> {

  @Nullable P currentParam;

  public ParametrisedAsyncProvider(@NonNull ExecutorService executor,
      @NonNull Handler mainThreadHook) {
    super(executor, mainThreadHook);
  }

  /**
   * вызов операции с параметром
   *
   * @param param объект параметра
   * @param listener объект слушателя результата
   */
  public void provide(P param, ProviderListener<T> listener) {
    currentParam = param;
    super.provide(listener);
  }

  /**
   * вызов операции без параметра(вместо параметра будет null)
   *
   * @param callback объект слушателя результата
   */
  @Override public void provide(@NonNull ProviderListener<T> callback) {
    currentParam = null;
    super.provide(callback);
  }
}
