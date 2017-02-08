package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.BindString;
import java.util.List;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.ui.adapter.StudentPickerAdapter;

/**
 * экран выбора студента
 */
public final class StudentPickerFragment extends StudentsListFragment {

  @BindString(R.string.page_name_picker) String pickerTitle;

  private OnStudentPickerListener pickerListener;

  /**
   * создание объекта экрана выбора студента
   *
   * @return объект экрана
   */
  @NonNull public static StudentPickerFragment newInstance() {
    return new StudentPickerFragment();
  }

  /**
   * создание экрана, в нём происходит запрос на получение прав доступа
   *
   * @param savedInstanceState сохранённое состояние, не используется здесь
   */
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    adapter = new StudentPickerAdapter();
  }

  /**
   * выбран студент, объект отправляется вызывающей стороне, а экран закрывается
   *
   * @param item объект с данными студента
   */
  @Override public void onItemClick(@NonNull Student item) {
    if (pickerListener != null) {
      pickerListener.onStudentPicked(item);
      navigateUp();
    }
  }

  /**
   * результат провайдера
   *
   * @param payload список всех студентов
   */
  @Override public void onProviderResult(@NonNull List<Student> payload) {
    super.onProviderResult(payload);
    add.setVisibility(View.GONE);
  }

  /**
   * возвращает название экрана
   *
   * @return строка с названием
   */
  @NonNull @Override public String getTitle() {
    return pickerTitle;
  }

  /**
   * возвращает флаг о наличии кнопки назад
   *
   * @return флаг наличия
   */
  @Override boolean hasBackButton() {
    return true;
  }

  /**
   * подключение слушателя выбора студента
   *
   * @param pickerListener объект слушателя
   */
  final public void setPickerListener(@Nullable OnStudentPickerListener pickerListener) {
    this.pickerListener = pickerListener;
  }

  /**
   * слушатель выбора студентов
   */
  public interface OnStudentPickerListener {
    void onStudentPicked(@NonNull Student student);
  }
}
