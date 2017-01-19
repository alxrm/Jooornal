package rm.com.jooornal.data.provider;

import android.support.annotation.NonNull;

/**
 * Created by alex
 */
public interface AsyncProvider<T> {
  void provide(@NonNull ProviderListener<T> callback);
}
