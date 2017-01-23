package rm.com.jooornal.ui.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Phone;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.util.Converters;
import rm.com.jooornal.util.Events;

/**
 * Created by alex
 */
public final class StudentCreateFragment extends BaseFragment
    implements DatePickerDialog.OnDateSetListener {

  @BindString(R.string.page_name_student_create) String title;
  @BindView(R.id.student_create_input_birthday) TextView birthday;

  private final Student student = new Student();
  private final Phone main = new Phone();
  private final Phone alter = new Phone();

  private boolean hasCalendarPermission = false;

  {
    main.student = student;
    alter.student = student;
  }

  @NonNull public static StudentCreateFragment newInstance() {
    return new StudentCreateFragment();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    askAndSavePermission();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_student_create, container, false);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_create_new, menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_create_done) {
      addStudent();
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public void onPermissionDenied(List<DeniedPermission> deniedPermissionList) {
    Toast.makeText(getActivity(), "День рождения не будет записан в календарь", Toast.LENGTH_LONG)
        .show();
  }

  @OnTextChanged(R.id.student_create_input_surname)
  final void onSurnameChanged(CharSequence studentLastName) {
    student.surname = studentLastName.toString();
  }

  @OnTextChanged(R.id.student_create_input_name)
  final void onFirstNameChanged(CharSequence studentFirstName) {
    student.name = studentFirstName.toString();
  }

  @OnTextChanged(R.id.student_create_input_patronymic)
  final void onPatronymicChanged(CharSequence patronymic) {
    student.patronymic = patronymic.toString();
  }

  @OnTextChanged(R.id.student_create_input_phone)
  final void onMainPhoneChanged(CharSequence phoneNumber) {
    main.phoneNumber = phoneNumber.toString();
  }

  @OnTextChanged(R.id.student_create_input_phone_name)
  final void onMainPhoneNameChanged(CharSequence name) {
    main.alias = name.toString();
  }

  @OnTextChanged(R.id.student_create_input_altphone)
  final void onAlterPhoneChanged(CharSequence phoneNumber) {
    alter.phoneNumber = phoneNumber.toString();
  }

  @OnTextChanged(R.id.student_create_input_altphone_name)
  final void onAlterPhoneNameChanged(CharSequence name) {
    alter.alias = name.toString();
  }

  @OnClick(R.id.student_create_wrapper_birthday) final void onSetBirthday() {
    final Calendar birth = Calendar.getInstance();
    final DatePickerDialog dpd =
        DatePickerDialog.newInstance(this, birth.get(Calendar.YEAR), birth.get(Calendar.MONTH),
            birth.get(Calendar.DAY_OF_MONTH));

    dpd.show(getFragmentManager(), "dpd");
  }

  @Override
  public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
    final long time = Converters.timeOf(year, monthOfYear, dayOfMonth);

    student.birthDate = time;
    birthday.setText(Converters.dateStringOf(time));
  }

  @NonNull @Override public String getTitle() {
    return title;
  }

  @Override boolean hasBackButton() {
    return true;
  }

  @Override boolean isNested() {
    return false;
  }

  @AskPermission({ Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR })
  private void askAndSavePermission() {
    hasCalendarPermission = true;
  }

  private void addStudent() {
    final boolean hasName = !student.name.isEmpty();
    final boolean hasSurname = !student.surname.isEmpty();
    final boolean hasPhone = !main.phoneNumber.isEmpty();
    final boolean hasAlterPhone = !alter.phoneNumber.isEmpty();
    final boolean hasBirthday = student.birthDate != 0L;
    final boolean isInvalid = !(hasName && hasSurname && hasPhone && hasBirthday);

    if (isInvalid) {
      Toast.makeText(getActivity(), "Не все обязательные поля заполнены", Toast.LENGTH_LONG).show();
      return;
    }

    if (hasAlterPhone) {
      alter.save();
    }

    main.save();
    student.save();

    if (hasCalendarPermission) {
      addCalendarEvent();
    }

    navigateUp();
  }

  private void addCalendarEvent() {
    Events.addEventToCalender(getActivity().getContentResolver(),
        student.name + " " + student.surname + " — " + " день рождения", "", null,
        student.birthDate, true);
  }
}
