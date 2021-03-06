package rm.com.jooornal.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Call;
import rm.com.jooornal.ui.holder.BaseHolder;
import rm.com.jooornal.ui.holder.CallHolder;

/**
 * класс привязки списка звонков к интерфейсу
 */
public final class CallsListAdapter extends BaseAdapter<Call, BaseHolder<Call>> {

  /**
   * создание интерфейса элемента списка
   *
   * @param parent интерфейс списка
   * @param viewType тип элемента списка
   * @return объект интерфейса
   */
  @Override public BaseHolder<Call> onCreateViewHolder(ViewGroup parent, int viewType) {
    return new CallHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call, parent, false));
  }
}
