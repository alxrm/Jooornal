package rm.com.jooornal.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Sms;
import rm.com.jooornal.ui.holder.BaseHolder;
import rm.com.jooornal.ui.holder.SmsHolder;

/**
 * Created by alex
 */

public final class SmsListAdapter extends BaseAdapter<Sms, BaseHolder<Sms>> {

  @Override public BaseHolder<Sms> onCreateViewHolder(ViewGroup parent, int viewType) {
    return new SmsHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sms, parent, false));
  }
}
