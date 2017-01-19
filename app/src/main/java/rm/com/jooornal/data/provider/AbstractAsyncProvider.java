package rm.com.jooornal.data.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import java.util.concurrent.ExecutorService;
import rm.com.jooornal.util.Conditions;

/**
 * Created by alex
 */

@SuppressWarnings("WeakerAccess")
abstract class AbstractAsyncProvider<T> implements AsyncProvider<T> {

  protected final ExecutorService executor;
  protected final Handler mainThreadHook;

  protected volatile T cachedResult;

  public AbstractAsyncProvider(@NonNull ExecutorService executor, @NonNull Handler mainThreadHook) {
    this.executor = executor;
    this.mainThreadHook = mainThreadHook;
  }

  @Override public void provide(@NonNull final ProviderListener<T> callback) {
    Conditions.checkNotNull(callback);

    executor.submit(new Runnable() {
      @Override public void run() {
        cachedResult = get();
        postCallback(cachedResult, callback);
      }
    });
  }

  protected abstract T get();

  protected void postCallback(final T result, final ProviderListener<T> callback) {
    mainThreadHook.post(new Runnable() {
      @Override public void run() {
        callback.onProvide(result);
      }
    });
  }
}
