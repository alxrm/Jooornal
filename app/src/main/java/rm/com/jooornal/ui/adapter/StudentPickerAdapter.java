package rm.com.jooornal.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.ui.holder.BaseHolder;
import rm.com.jooornal.ui.holder.StudentHolder;

/**
 * класс привязки списка с выбором студентов к интерфейсу
 */
public final class StudentPickerAdapter extends StudentsListAdapter {

  /**
   * создание интерфейса элемента списка
   *
   * @param parent интерфейс списка
   * @param viewType тип элемента списка
   * @return объект интерфейса
   */
  @Override public BaseHolder<Student> onCreateViewHolder(ViewGroup parent, int viewType) {
    return new StudentHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false));
  }
}
