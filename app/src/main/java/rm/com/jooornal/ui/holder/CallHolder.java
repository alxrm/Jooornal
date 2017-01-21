package rm.com.jooornal.ui.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Call;
import rm.com.jooornal.util.Converters;

/**
 * Created by alex
 */

public final class CallHolder extends BaseHolder<Call> {

  @BindView(R.id.item_call_from) TextView from;
  @BindView(R.id.item_call_date) TextView date;

  public CallHolder(View itemView) {
    super(itemView);
  }

  @Override public void bind(@NonNull Call model) {
    from.setText(model.phone.phoneNumber);
    date.setText(Converters.dateStringOf(model.time));
  }
}
