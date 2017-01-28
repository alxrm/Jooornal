package rm.com.jooornal.data.provider;

import android.support.annotation.NonNull;

interface AsyncProvider<T> {
  void provide(@NonNull ProviderListener<T> callback);
}
