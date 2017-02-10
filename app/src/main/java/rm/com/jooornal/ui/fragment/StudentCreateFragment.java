package rm.com.jooornal.ui.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import com.canelmas.let.AskPermission;
import com.canelmas.let.DeniedPermission;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Phone;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.inject.qualifiers.BirthdayNotifications;
import rm.com.jooornal.util.Converters;
import rm.com.jooornal.util.Events;

/**
 * экран добавления студента
 */
public class StudentCreateFragment extends BaseFormFragment
    implements DatePickerDialog.OnDateSetListener {

  @BindString(R.string.page_name_student_create) String title;
  @BindString(R.string.message_form_not_correct) String messageFormNotCorrect;
  @BindString(R.string.message_birthday_ignored) String messageBirthdayIgnored;

  @BindView(R.id.student_create_input_birthday) TextView birthday;

  @Inject ContentResolver contentResolver;
  @Inject @BirthdayNotifications Provider<Boolean> shouldNotify;

  protected Student student = new Student();
  protected Phone main = new Phone();
  protected Phone alter = new Phone();

  private boolean hasCalendarPermission = false;

  /**
   * блок инициализации полей
   */ {
    main.student = student;
    alter.student = student;
  }

  /**
   * создание объекта экрана добавления студента
   *
   * @return объект экрана
   */
  @NonNull public static StudentCreateFragment newInstance() {
    return new StudentCreateFragment();
  }

  /**
   * создание экрана, в нём происходит запрос на получение прав доступа
   *
   * @param savedInstanceState сохранённое состояние, не используется здесь
   */
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    askAndSaveCalendarPermission();
  }

  /**
   * создание интерфейса экрана
   *
   * @param inflater объект создания объекта интерфейса из XML вёрстки
   * @param container родительский объект интерфейса
   * @param savedInstanceState сохранённое состояние экрана(не используется)
   * @return объект созданного интерфейса
   */
  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_student_create, container, false);
  }

  /**
   * в доступе отказано, выводится всплывающее сообщение с пояснением
   *
   * @param deniedPermissionList список прав доступа, разрешение на которые не получено
   */
  @Override public void onPermissionDenied(List<DeniedPermission> deniedPermissionList) {
    Toast.makeText(getActivity(), messageBirthdayIgnored, Toast.LENGTH_LONG).show();
  }

  /**
   * внедрение зависимостей
   *
   * @param app объект приложения, через который можно вызвать контейнер зависимостей
   */
  @Override protected void injectDependencies(@NonNull JooornalApplication app) {
    super.injectDependencies(app);
    app.injector().inject(this);
  }

  /**
   * изменена фамилия
   *
   * @param studentLastName строка с фамилией
   */
  @OnTextChanged(R.id.student_create_input_surname)
  final void onSurnameChanged(CharSequence studentLastName) {
    student.surname = studentLastName.toString();
  }

  /**
   * изменено имя
   *
   * @param studentFirstName строка с именем
   */
  @OnTextChanged(R.id.student_create_input_name)
  final void onFirstNameChanged(CharSequence studentFirstName) {
    student.name = studentFirstName.toString();
  }

  /**
   * изменено отчество
   *
   * @param patronymic строка с отчеством
   */
  @OnTextChanged(R.id.student_create_input_patronymic)
  final void onPatronymicChanged(CharSequence patronymic) {
    student.patronymic = patronymic.toString();
  }

  /**
   * изменён первый номер телефона
   *
   * @param phoneNumber строка с номером
   */
  @OnTextChanged(R.id.student_create_input_phone)
  final void onMainPhoneChanged(CharSequence phoneNumber) {
    main.phoneNumber = Converters.databasePhoneNumberOf(phoneNumber.toString());
  }

  /**
   * изменено название первого номера телефона
   *
   * @param name строка с названием
   */
  @OnTextChanged(R.id.student_create_input_phone_name)
  final void onMainPhoneNameChanged(CharSequence name) {
    main.alias = name.toString();
  }

  /**
   * изменён второй номер телефона
   *
   * @param phoneNumber строка с номером
   */
  @OnTextChanged(R.id.student_create_input_altphone)
  final void onAlterPhoneChanged(CharSequence phoneNumber) {
    alter.phoneNumber = Converters.databasePhoneNumberOf(phoneNumber.toString());
  }

  /**
   * изменено название второго номера телефона
   *
   * @param name строка с названием
   */
  @OnTextChanged(R.id.student_create_input_altphone_name)
  final void onAlterPhoneNameChanged(CharSequence name) {
    alter.alias = name.toString();
  }

  /**
   * выбор даты рождения, открывается диалог с выбором даты
   */
  @OnClick(R.id.student_create_wrapper_birthday) final void onSetBirthday() {
    final Calendar birth = Calendar.getInstance();
    final DatePickerDialog dpd =
        DatePickerDialog.newInstance(this, birth.get(Calendar.YEAR), birth.get(Calendar.MONTH),
            birth.get(Calendar.DAY_OF_MONTH));

    dpd.show(getFragmentManager(), "dpd");
  }

  /**
   * дата рождения выбрана
   *
   * @param view объект интерфейса с диалогом
   * @param year номер года
   * @param monthOfYear номер месяца
   * @param dayOfMonth номер дня
   */
  @Override public void onDateSet(DatePickerDialog view, int year, int monthOfYear,
      int dayOfMonth) {
    final long time = Converters.timeOf(year, monthOfYear, dayOfMonth);

    student.birthDate = time;
    birthday.setText(Converters.dateStringOf(time));
  }

  /**
   * возвращает название экрана
   *
   * @return строка с названием
   */
  @NonNull @Override public String getTitle() {
    return title;
  }

  /**
   * сохранение данных студента в БД
   *
   * @return возвращает флаг, успешно ли произошло сохранение
   */
  @Override boolean saveFormData() {
    final boolean hasName = !student.name.isEmpty();
    final boolean hasSurname = !student.surname.isEmpty();
    final boolean hasPhone = !main.phoneNumber.isEmpty();
    final boolean hasAlterPhone = !alter.phoneNumber.isEmpty();
    final boolean hasBirthday = student.birthDate != 0L;
    final boolean isInvalid = !(hasName && hasSurname && hasPhone && hasBirthday);

    if (isInvalid) {
      return false;
    }

    if (hasAlterPhone) {
      alter.save();
    } else {
      alter.delete();
    }

    if (hasCalendarPermission && shouldNotify.get()) {
      addCalendarEvent();
    }

    main.save();
    student.refreshPhones();
    student.save();

    return true;
  }

  /**
   * показывает всплывающее сообщение в случае ошибочных данных или их нехватки, при заполнении
   * формы
   */
  @Override void showInvalidDataError() {
    Toast.makeText(getActivity(), messageFormNotCorrect, Toast.LENGTH_LONG).show();
  }

  /**
   * запрос на разрешение прав доступа к системному календарю
   */
  @AskPermission({
      Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR
  }) private void askAndSaveCalendarPermission() {
    hasCalendarPermission = true;
  }

  /**
   * добавление события о дне рождения студента в календарь
   */
  private void addCalendarEvent() {
    final String title = getString(R.string.student_birthday_event, student.name, student.surname);

    student.birthDayEventId =
        Events.addEventToCalendar(contentResolver, title, "", student.birthDate, true);
  }
}
