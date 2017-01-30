package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import butterknife.BindString;
import butterknife.BindView;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.util.Conditions;
import rm.com.jooornal.util.Converters;

public final class StudentEditFragment extends StudentCreateFragment {
  private static final String KEY_STUDENT = "KEY_STUDENT";

  @BindString(R.string.page_name_student_edit) String titleEdit;

  @BindView(R.id.student_create_input_name) EditText name;
  @BindView(R.id.student_create_input_surname) EditText surname;
  @BindView(R.id.student_create_input_patronymic) EditText patronymic;
  @BindView(R.id.student_create_input_phone) EditText mainPhone;
  @BindView(R.id.student_create_input_altphone) EditText alterPhone;
  @BindView(R.id.student_create_input_phone_name) EditText mainPhoneName;
  @BindView(R.id.student_create_input_altphone_name) EditText alterPhoneName;

  public static StudentEditFragment newInstance(@NonNull Student student) {
    Bundle args = new Bundle();
    StudentEditFragment fragment = new StudentEditFragment();

    args.putParcelable(KEY_STUDENT, student);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    name.setText(student.name);
    surname.setText(student.surname);
    patronymic.setText(student.patronymic);
    mainPhone.setText(main.phoneNumber);
    alterPhone.setText(alter.phoneNumber);
    mainPhoneName.setText(main.alias);
    alterPhoneName.setText(alter.alias);
    birthday.setText(Converters.dateStringOf(student.birthDate));
  }

  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    student = args.getParcelable(KEY_STUDENT);
    Conditions.checkNotNull(student);

    this.main = student.getPhones().get(0);

    if (student.getPhones().size() > 1) {
      this.alter = student.getPhones().get(1);
    }
  }

  @NonNull @Override public String getTitle() {
    return titleEdit;
  }
}
