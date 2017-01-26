package rm.com.jooornal.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.ui.holder.BaseHolder;
import rm.com.jooornal.ui.holder.StudentDetailedHolder;

/**
 * Created by alex
 */

public class StudentsListAdapter extends BaseAdapter<Student, BaseHolder<Student>> {

  @Override public BaseHolder<Student> onCreateViewHolder(ViewGroup parent, int viewType) {
    return new StudentDetailedHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false));
  }
}
