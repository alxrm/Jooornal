package rm.com.jooornal.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Sms;
import rm.com.jooornal.ui.holder.BaseHolder;
import rm.com.jooornal.ui.holder.SmsHolder;

/**
 * класс привязки списка СМС сообщений к интерфейсу
 */
public final class SmsListAdapter extends BaseAdapter<Sms, BaseHolder<Sms>> {

  /**
   * создание интерфейса элемента списка
   *
   * @param parent интерфейс списка
   * @param viewType тип элемента списка
   * @return объект интерфейса
   */
  @Override public BaseHolder<Sms> onCreateViewHolder(ViewGroup parent, int viewType) {
    return new SmsHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sms, parent, false));
  }
}
