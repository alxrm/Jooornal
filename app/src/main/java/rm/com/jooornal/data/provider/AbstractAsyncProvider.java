package rm.com.jooornal.data.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import java.util.concurrent.ExecutorService;
import rm.com.jooornal.util.Conditions;

@SuppressWarnings("WeakerAccess")
/**
 * абстрактный класс асинхронного провайдера, здесь содержится базовая логика асинхронной операции
 */ abstract class AbstractAsyncProvider<T> implements AsyncProvider<T> {

  protected final ExecutorService executor;
  protected final Handler mainThreadHook;

  protected volatile T cachedResult;

  public AbstractAsyncProvider(@NonNull ExecutorService executor, @NonNull Handler mainThreadHook) {
    this.executor = executor;
    this.mainThreadHook = mainThreadHook;
  }

  /**
   * создание новой задачи в очереди в другом потоке, чтобы операция выполнялась асинхронно
   *
   * @param callback объект слушателя результата
   */
  @Override public void provide(@NonNull final ProviderListener<T> callback) {
    Conditions.checkNotNull(callback);

    executor.submit(new Runnable() {
      @Override public void run() {
        cachedResult = execute();

        if (cachedResult != null) {
          postCallback(cachedResult, callback);
        }
      }
    });
  }

  /**
   * получение результата
   *
   * @return объект результата
   */
  protected abstract T execute();

  /**
   * создание задачи в главном потоке, в которой слушателю передаётся объект с результатом операции,
   * чтобы результат асинхронной операции вернулся к вызывающей стороне
   *
   * @param result объект результата
   * @param callback объект слушателя результата
   */
  protected void postCallback(final T result, final ProviderListener<T> callback) {
    mainThreadHook.post(new Runnable() {
      @Override public void run() {
        callback.onProviderResult(result);
      }
    });
  }
}
