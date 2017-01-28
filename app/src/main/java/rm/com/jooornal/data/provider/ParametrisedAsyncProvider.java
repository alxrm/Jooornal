package rm.com.jooornal.data.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.concurrent.ExecutorService;

public abstract class ParametrisedAsyncProvider<P, T> extends AbstractAsyncProvider<T> {

  @Nullable P currentParam;

  public ParametrisedAsyncProvider(@NonNull ExecutorService executor,
      @NonNull Handler mainThreadHook) {
    super(executor, mainThreadHook);
  }

  public void provide(P param, ProviderListener<T> listener) {
    currentParam = param;
    super.provide(listener);
  }

  @Override public void provide(@NonNull ProviderListener<T> callback) {
    currentParam = null;
    super.provide(callback);
  }
}
