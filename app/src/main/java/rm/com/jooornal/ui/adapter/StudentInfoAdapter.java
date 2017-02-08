package rm.com.jooornal.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import rm.com.jooornal.constant.StudentInfo;
import rm.com.jooornal.data.entity.StudentInfoEntry;
import rm.com.jooornal.ui.holder.BaseHolder;
import rm.com.jooornal.ui.holder.TextEntryHolder;
import rm.com.jooornal.ui.holder.TitleEntryHolder;

import static rm.com.jooornal.constant.StudentInfo.ENTRY_LAYOUTS;

/**
 * класс привязки списка разбитых данных о студенте к интерфейсу
 */
public final class StudentInfoAdapter
    extends BaseAdapter<StudentInfoEntry, BaseHolder<StudentInfoEntry>> {

  /**
   * тип элемента в списке разделённых данных
   *
   * @param position позиция элемента
   * @return номер типа
   */
  @Override public int getItemViewType(int position) {
    return data.get(position).type;
  }

  /**
   * создание интерфейса элемента списка
   *
   * @param parent интерфейс списка
   * @param viewType тип элемента списка
   * @return объект интерфейса
   */
  @Override public BaseHolder<StudentInfoEntry> onCreateViewHolder(ViewGroup parent, int viewType) {
    final View itemView = LayoutInflater.from(parent.getContext())
        .inflate(ENTRY_LAYOUTS.get(viewType), parent, false);

    switch (viewType) {
      case StudentInfo.ENTRY_TITLE:
        return new TitleEntryHolder(itemView);
      case StudentInfo.ENTRY_TEXT:
        return new TextEntryHolder(itemView);
      default:
        throw new IllegalStateException("Unknown entry type");
    }
  }
}
