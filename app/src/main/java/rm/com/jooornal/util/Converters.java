package rm.com.jooornal.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import rm.com.jooornal.constant.Colors;
import rm.com.jooornal.constant.Formats;
import rm.com.jooornal.data.entity.Phone;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.data.entity.StudentInfoEntry;

import static rm.com.jooornal.constant.StudentInfo.ENTRY_TEXT;
import static rm.com.jooornal.constant.StudentInfo.ENTRY_TITLE;

/**
 * Created by alex
 */

public final class Converters {
  private static final String DOT = ".";
  private static final String EMPTY = "";

  private Converters() {
  }

  @NonNull public static String shortNameOf(@NonNull Student student) {
    return shortNameOf(student.surname, student.name, student.patronymic);
  }

  @NonNull public static String shortNameOf(@NonNull String surname, @NonNull String name,
      @Nullable String patronymic) {
    final String patronymicInitial =
        ((patronymic == null) || patronymic.isEmpty()) ? EMPTY : (patronymic.substring(0, 1) + DOT);
    final String nameInitial = name.substring(0, 1) + DOT;

    return String.format("%s %s %s", surname, nameInitial, patronymicInitial);
  }

  @NonNull public static String iconLettersOf(@NonNull String surname, @NonNull String name) {
    return name.substring(0, 1) + surname.substring(0, 1);
  }

  @NonNull public static String dateStringOf(long time) {
    return dateStringOf(time, Formats.PATTERN_REGULAR_DATE);
  }

  @NonNull public static String shortDateStringOf(long time) {
    return dateStringOf(time, Formats.PATTERN_SHORT_DATE);
  }

  @NonNull public static List<StudentInfoEntry> infoEntryListOf(@NonNull Student student) {
    final List<StudentInfoEntry> infoEntries = new ArrayList<>();
    final StringBuilder phonesStringBuilder = new StringBuilder();

    infoEntries.add(new StudentInfoEntry(ENTRY_TITLE, "Полное имя"));
    infoEntries.add(new StudentInfoEntry(ENTRY_TEXT,
        String.format("%s %s %s", student.name, student.surname, student.patronymic)));

    infoEntries.add(new StudentInfoEntry(ENTRY_TITLE, "Дата рождения"));
    infoEntries.add(new StudentInfoEntry(ENTRY_TEXT, dateStringOf(student.birthDate)));

    infoEntries.add(new StudentInfoEntry(ENTRY_TITLE, "Контакты"));

    Logger.d(student.getPhones().size());

    for (Phone phone : student.getPhones()) {
      phonesStringBuilder.append(phone.phoneNumber);

      if (!phone.alias.isEmpty()) {
        phonesStringBuilder.append(" (").append(phone.alias).append(")");
      }

      phonesStringBuilder.append("\n");
    }

    infoEntries.add(new StudentInfoEntry(ENTRY_TEXT, phonesStringBuilder.toString().trim()));

    return infoEntries;
  }

  public static int colorOf(@NonNull String surname) {
    final int size = Colors.ICON_COLORS.length;
    final int nameHash = Math.abs(surname.hashCode());

    return Colors.ICON_COLORS[nameHash % size];
  }

  public static long timeOf(int year, int monthOfYear, int dayOfMonth) {
    final Calendar calendar = Calendar.getInstance();
    calendar.set(year, monthOfYear, dayOfMonth);
    return calendar.getTimeInMillis();
  }

  @NonNull private static String dateStringOf(long time, @NonNull String pattern) {
    final Date date = new Date(time);
    final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, new Locale("ru", "RU"));

    return dateFormat.format(date);
  }
}
