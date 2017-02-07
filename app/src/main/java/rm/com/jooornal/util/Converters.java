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

/**
 * утилитарный класс с методами для преобразования одних данных в другие
 */
public final class Converters {
  private static final String DOT = ".";

  private Converters() {
  }

  /**
   * преобразует объект студента в строку с инициалами
   *
   * @param student объект студента, данные которого будут использованы
   * @return строку с инициалами
   */
  @NonNull public static String shortNameOf(@NonNull Student student) {
    return shortNameOf(student.surname, student.name, student.patronymic);
  }

  /**
   * преобразует объект студента в строку с полным именем
   *
   * @param student объект студента, данные которого будут использованы
   * @return строку с полным именем
   */
  @NonNull public static String fullNameOf(@NonNull Student student) {
    return String.format("%s %s %s", student.surname, student.name, student.patronymic);
  }

  /**
   * преобразует имя-фамилию в две буквы для иконки
   *
   * @param surname строка с фамилией
   * @param name строка с именем
   * @return строку с двумя буквами
   */
  @NonNull public static String iconLettersOf(@NonNull String surname, @NonNull String name) {
    return name.substring(0, 1) + surname.substring(0, 1);
  }

  /**
   * преобразует миллисекунды в строку с датой
   *
   * @param time время в миллисекундах
   * @return строку с датой
   */
  @NonNull public static String dateStringOf(long time) {
    return dateStringOf(time, Formats.PATTERN_REGULAR_DATE);
  }

  /**
   * преобразует миллисекунды в строку с датой и временем
   *
   * @param time время с миллисекундах
   * @return строка с датой и временем
   */
  @NonNull public static String timedDateStringOf(long time) {
    return dateStringOf(time, Formats.PATTERN_TIMED_DATE);
  }

  /**
   * преобразует строку с номером телефона в формат, подходящий для БД
   *
   * @param phoneNumber строка с номером телефона
   * @return та же строка, но в формате для БД
   */
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

  /**
   * преобразует строку с номером телефона в формат, который можно выводить в списке
   *
   * @param phoneNumber строка с номером телефона без форматирования
   * @return отформатированную строку
   */
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

  /**
   * разбивает информацию о студенте на несколько кусков для отображении в виде списка
   *
   * @param student объект с данными о студенте
   * @return список с разделённой информацией
   */
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

  /**
   * преобразует строку с фамилией в цвет иконки в списке студентов
   *
   * @param surname строка с фамилией
   * @return число в шестнадцатиричном формате, для кодирования цвета
   */
  public static int colorOf(@NonNull String surname) {
    final int size = Colors.STUDENT_ICON_COLORS.length;
    final int nameHash = Math.abs(surname.hashCode());

    return Colors.STUDENT_ICON_COLORS[nameHash % size];
  }

  /**
   * получает время в миллисекундах из года, месяца и дня
   *
   * @param year число с годом
   * @param monthOfYear число с месяцем
   * @param dayOfMonth число с днём
   * @return время в миллисекундах
   */
  public static long timeOf(int year, int monthOfYear, int dayOfMonth) {
    final Calendar calendar = Calendar.getInstance();
    calendar.set(year, monthOfYear, dayOfMonth);
    return calendar.getTimeInMillis();
  }

  /**
   * получение инициалов по строкам с именем, фамилией и отчеством
   *
   * @param surname строка с фамилией
   * @param name строка с именем
   * @param patronymic строка с отчеством
   * @return строка с инициалами
   */
  @NonNull private static String shortNameOf(@NonNull String surname, @NonNull String name,
      @Nullable String patronymic) {
    final String nameInitial = name.substring(0, 1) + DOT;
    final String patronymicInitial =
        ((patronymic == null) || patronymic.isEmpty()) ? "" : (patronymic.substring(0, 1) + DOT);

    return String.format("%s %s %s", surname, nameInitial, patronymicInitial);
  }

  /**
   * возвращает отформатированную по определённому паттерну строку с датой
   *
   * @param time время в миллисекудах
   * @param pattern строка с паттерном
   * @return
   */
  @NonNull private static String dateStringOf(long time, @NonNull String pattern) {
    final Date date = new Date(time);
    final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, new Locale("ru", "RU"));

    return dateFormat.format(date);
  }
}
