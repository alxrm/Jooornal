package rm.com.jooornal.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.redmadrobot.inputmask.helper.Mask;
import com.redmadrobot.inputmask.model.CaretString;
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

import static rm.com.jooornal.constant.Formats.MASK_PHONE_CELL;
import static rm.com.jooornal.constant.Formats.MASK_PHONE_HOME;
import static rm.com.jooornal.constant.Formats.PHONE_LENGTH_FULL;
import static rm.com.jooornal.constant.Formats.PHONE_LENGTH_HOME;
import static rm.com.jooornal.constant.Formats.PHONE_PREFIX_COUNTRY;
import static rm.com.jooornal.constant.Formats.PHONE_PREFIX_COUNTRY_REGEX;
import static rm.com.jooornal.constant.Formats.PHONE_PREFIX_HOME;
import static rm.com.jooornal.constant.Formats.PHONE_PREFIX_HOME_REGEX;
import static rm.com.jooornal.constant.StudentInfo.ENTRY_TEXT;
import static rm.com.jooornal.constant.StudentInfo.ENTRY_TITLE;

public final class Converters {
  private static final String DOT = ".";

  private Converters() {
  }

  @NonNull public static String shortNameOf(@NonNull Student student) {
    return shortNameOf(student.surname, student.name, student.patronymic);
  }

  @NonNull public static String fullNameOf(@NonNull Student student) {
    return String.format("%s %s %s", student.surname, student.name, student.patronymic);
  }

  @NonNull public static String iconLettersOf(@NonNull String surname, @NonNull String name) {
    return name.substring(0, 1) + surname.substring(0, 1);
  }

  @NonNull public static String dateStringOf(long time) {
    return dateStringOf(time, Formats.PATTERN_REGULAR_DATE);
  }

  @NonNull public static String timedDateStringOf(long time) {
    return dateStringOf(time, Formats.PATTERN_TIMED_DATE);
  }

  @NonNull public static String databasePhoneNumberOf(@NonNull String phoneNumber) {
    final String cleanNumber = phoneNumber.replaceAll("-", "");
    final boolean hasWrongCountryCode = cleanNumber.startsWith("8");

    if (cleanNumber.length() == PHONE_LENGTH_HOME) {
      return PHONE_PREFIX_HOME + cleanNumber;
    }

    if (cleanNumber.length() >= PHONE_LENGTH_FULL && hasWrongCountryCode) {
      return cleanNumber.replaceFirst("8", PHONE_PREFIX_COUNTRY_REGEX);
    }

    return cleanNumber;
  }

  @NonNull public static String formatPhoneNumberOf(@NonNull String phoneNumber) {
    final boolean isHome = phoneNumber.contains(PHONE_PREFIX_HOME);
    final boolean isCell = phoneNumber.contains(PHONE_PREFIX_COUNTRY) || phoneNumber.length() > 11;

    if (isHome) {
      final String simplified = phoneNumber.replaceAll(PHONE_PREFIX_HOME_REGEX, "");
      final CaretString caret = new CaretString(simplified, simplified.length());
      final Mask.Result result = MASK_PHONE_HOME.apply(caret, true);

      return result.getFormattedText().getString();
    }

    if (isCell) {
      final CaretString caret = new CaretString(phoneNumber, phoneNumber.length());
      final Mask.Result result = MASK_PHONE_CELL.apply(caret, true);

      return result.getFormattedText().getString();
    }

    return phoneNumber;
  }

  @NonNull public static List<StudentInfoEntry> infoEntryListOf(@NonNull Student student) {
    final List<StudentInfoEntry> infoEntries = new ArrayList<>();
    final StringBuilder phonesStringBuilder = new StringBuilder();

    infoEntries.add(new StudentInfoEntry(ENTRY_TITLE, "Полное имя"));
    infoEntries.add(new StudentInfoEntry(ENTRY_TEXT, fullNameOf(student)));

    infoEntries.add(new StudentInfoEntry(ENTRY_TITLE, "Дата рождения"));
    infoEntries.add(new StudentInfoEntry(ENTRY_TEXT, dateStringOf(student.birthDate)));

    infoEntries.add(new StudentInfoEntry(ENTRY_TITLE, "Контакты"));

    for (Phone phone : student.getPhones()) {
      phonesStringBuilder.append(formatPhoneNumberOf(phone.phoneNumber));

      if (!phone.alias.isEmpty()) {
        phonesStringBuilder.append(" (").append(phone.alias).append(")");
      }

      phonesStringBuilder.append("\n");
    }

    infoEntries.add(new StudentInfoEntry(ENTRY_TEXT, phonesStringBuilder.toString().trim()));

    return infoEntries;
  }

  public static int colorOf(@NonNull String surname) {
    final int size = Colors.STUDENT_ICON_COLORS.length;
    final int nameHash = Math.abs(surname.hashCode());

    return Colors.STUDENT_ICON_COLORS[nameHash % size];
  }

  public static long timeOf(int year, int monthOfYear, int dayOfMonth) {
    final Calendar calendar = Calendar.getInstance();
    calendar.set(year, monthOfYear, dayOfMonth);
    return calendar.getTimeInMillis();
  }

  @NonNull private static String shortNameOf(@NonNull String surname, @NonNull String name,
      @Nullable String patronymic) {
    final String nameInitial = name.substring(0, 1) + DOT;
    final String patronymicInitial =
        ((patronymic == null) || patronymic.isEmpty()) ? "" : (patronymic.substring(0, 1) + DOT);

    return String.format("%s %s %s", surname, nameInitial, patronymicInitial);
  }

  @NonNull private static String dateStringOf(long time, @NonNull String pattern) {
    final Date date = new Date(time);
    final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, new Locale("ru", "RU"));

    return dateFormat.format(date);
  }
}
