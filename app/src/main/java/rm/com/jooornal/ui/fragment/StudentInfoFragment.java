package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import rm.com.jooornal.data.entity.Student;

/**
 * Created by alex
 */

public final class StudentInfoFragment extends BaseFragment {
  private static final String KEY_STUDENT = "KEY_STUDENT";

  private Student student;

  public static StudentInfoFragment newInstance(@NonNull Student student) {
    final Bundle args = new Bundle();
    final StudentInfoFragment fragment = new StudentInfoFragment();

    args.putParcelable(KEY_STUDENT, student);
    fragment.setArguments(args);

    return fragment;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    student = args.getParcelable(KEY_STUDENT);
  }

  @NonNull @Override String getTitle() {
    return "";
  }

  @Override boolean hasBackButton() {
    return false;
  }

  @Override boolean isNested() {
    return true;
  }
}
