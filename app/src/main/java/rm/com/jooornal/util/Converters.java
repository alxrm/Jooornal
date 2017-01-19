package rm.com.jooornal.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Calendar;
import java.util.Locale;

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

  @NonNull public static String dateStringOf(long time) {
    final Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(time);
    final int year = calendar.get(Calendar.YEAR);
    final int month = calendar.get(Calendar.MONTH) + 1;
    final int day = calendar.get(Calendar.DAY_OF_MONTH);

    return String.format(Locale.ENGLISH, "%02d.%02d.%d", day, month, year);
  }

  public static long timeOf(int year, int monthOfYear, int dayOfMonth) {
    final Calendar calendar = Calendar.getInstance();
    calendar.set(year, monthOfYear, dayOfMonth);
    return calendar.getTimeInMillis();
  }
}
