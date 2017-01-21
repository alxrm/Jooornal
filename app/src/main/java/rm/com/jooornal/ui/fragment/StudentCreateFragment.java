package rm.com.jooornal.ui.fragment;

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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.Calendar;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Phone;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.util.Converters;

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

  {
    main.student = student;
    alter.student = student;
  }

  public static StudentCreateFragment newInstance() {
    return new StudentCreateFragment();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_student_create, container, false);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_fragment_student_create, menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_student_create_done) {
      addStudent();
    }

    return super.onOptionsItemSelected(item);
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

  @OnClick(R.id.student_create_wrapper_birthday)
  final void onSetBirthday() {
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

    student.save();
    main.save();

    navigateUp();
  }
}
