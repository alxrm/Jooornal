package rm.com.jooornal.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by alex
 */

public final class Converters {
  private static final String DOT = ".";
  private static final String EMPTY = "";

  private Converters() {
  }

  @NonNull public static String initialsOf(@NonNull String surname, @NonNull String name,
      @Nullable String patronymic) {
    final String patronymicInitial =
        ((patronymic == null) || patronymic.isEmpty()) ? EMPTY : (patronymic.substring(0, 1) + DOT);
    final String nameInitial = name.substring(0, 1) + DOT;

    return String.format("%s %s %s", surname, nameInitial, patronymicInitial);
  }
}
