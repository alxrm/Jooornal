package rm.com.jooornal.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Call;
import rm.com.jooornal.ui.holder.BaseHolder;
import rm.com.jooornal.ui.holder.CallHolder;

/**
 * Created by alex
 */

public final class CallsListAdapter extends BaseAdapter<Call, BaseHolder<Call>> {
  @Override public BaseHolder<Call> onCreateViewHolder(ViewGroup parent, int viewType) {
    return new CallHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call, parent, false));
  }
}
