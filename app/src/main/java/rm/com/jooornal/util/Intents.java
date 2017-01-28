package rm.com.jooornal.util;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class Intents {
  private Intents() {
  }

  public static boolean checkIntent(@Nullable Intent intent, @NonNull String action) {
    return intent != null
        && action.equalsIgnoreCase(intent.getAction())
        && intent.getExtras() != null;
  }
}
