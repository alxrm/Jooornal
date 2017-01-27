package rm.com.jooornal.data.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import java.util.List;
import java.util.concurrent.ExecutorService;
import rm.com.jooornal.util.Lists;

/**
 * Created by alex
 */

@SuppressWarnings("WeakerAccess")
public abstract class ListAsyncProvider<T> extends AbstractAsyncProvider<List<T>> {

  public ListAsyncProvider(@NonNull ExecutorService executor, @NonNull Handler mainThreadHook) {
    super(executor, mainThreadHook);
  }

  public void search(@NonNull final String clause,
      @NonNull final ProviderListener<List<T>> callback) {
    if (cachedResult == null) {
      return;
    }

    if (clause.isEmpty()) {
      postCallback(cachedResult, callback);
      return;
    }

    executor.submit(new Runnable() {
      @Override public void run() {
        postCallback(searchByQuery(clause), callback);
      }
    });
  }

  protected abstract boolean matchQuery(T item, String query);

  private List<T> searchByQuery(final String clause) {
    return Lists.filter(cachedResult, new Lists.Predicate<T>() {
      @Override public boolean test(T item) {
        return matchQuery(item, clause);
      }
    });
  }
}
