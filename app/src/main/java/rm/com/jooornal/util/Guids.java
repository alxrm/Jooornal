package rm.com.jooornal.util;

import android.support.annotation.NonNull;
import java.util.UUID;

/**
 * Created by alex
 */

public final class Guids {

  private Guids() {
  }

  @NonNull public static String randomGuid() {
    return UUID.randomUUID().toString();
  }
}
