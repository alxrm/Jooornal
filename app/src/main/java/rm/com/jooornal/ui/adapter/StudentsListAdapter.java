package rm.com.jooornal.ui.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.ui.holder.BaseHolder;
import rm.com.jooornal.ui.holder.StudentHolder;
import rm.com.jooornal.util.Conditions;

/**
 * Created by alex
 */

public final class StudentsListAdapter extends BaseAdapter<Student, BaseHolder<Student>> {

  private BaseHolder.OnClickListener<Student> clickListener;

  @Override public BaseHolder<Student> onCreateViewHolder(ViewGroup parent, int viewType) {
    return new StudentHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false));
  }

  @Override public void onBindViewHolder(BaseHolder<Student> holder, int position) {
    super.onBindViewHolder(holder, position);
    holder.setOnClickListener(clickListener);
  }

  final public void setOnClickListener(@NonNull BaseHolder.OnClickListener<Student> clickListener) {
    Conditions.checkNotNull(clickListener);
    this.clickListener = clickListener;
  }
}