package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import java.util.List;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.ui.adapter.StudentPickerAdapter;

/**
 * Created by alex
 */

public final class StudentPickerFragment extends StudentsListFragment {

  private OnStudentPickerListener pickerListener;

  @NonNull public static StudentPickerFragment newInstance() {
    return new StudentPickerFragment();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    adapter = new StudentPickerAdapter();
  }

  @Override public void onItemClick(@NonNull Student item) {
    if (pickerListener != null) {
      pickerListener.onStudentPicked(item);
      navigateUp();
    }
  }

  @Override public void onProvide(@NonNull List<Student> payload) {
    super.onProvide(payload);
    add.setVisibility(View.GONE);
  }

  @NonNull @Override public String getTitle() {
    return "Выбрать студента";
  }

  @Override boolean hasBackButton() {
    return true;
  }

  final public void setPickerListener(@Nullable OnStudentPickerListener pickerListener) {
    this.pickerListener = pickerListener;
  }

  public interface OnStudentPickerListener {
    void onStudentPicked(@NonNull Student student);
  }
}
