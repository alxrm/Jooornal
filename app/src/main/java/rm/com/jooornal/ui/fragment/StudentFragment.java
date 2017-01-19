package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.util.Converters;

/**
 * Created by alex
 */

public final class StudentFragment extends BaseFragment {
  private static final String KEY_STUDENT = "KEY_STUDENT";

  private Student student;

  public static StudentFragment newInstance(@NonNull Student student) {
    final Bundle args = new Bundle();
    final StudentFragment fragment = new StudentFragment();

    args.putParcelable(KEY_STUDENT, student);
    fragment.setArguments(args);

    return fragment;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_student_info, container, false);
  }

  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    student = args.getParcelable(KEY_STUDENT);
  }

  @NonNull @Override String getTitle() {
    return Converters.initialsOf(student.surname, student.name, student.patronymic);
  }

  @Override boolean hasBackButton() {
    return true;
  }
}
