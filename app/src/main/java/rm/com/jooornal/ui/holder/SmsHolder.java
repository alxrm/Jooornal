package rm.com.jooornal.ui.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Sms;
import rm.com.jooornal.util.Converters;

/**
 * Created by alex
 */
public final class SmsHolder extends BaseHolder<Sms> {

  @BindView(R.id.item_message_from) TextView from;
  @BindView(R.id.item_message_date) TextView date;
  @BindView(R.id.item_message_text) TextView text;

  public SmsHolder(View itemView) {
    super(itemView);
  }

  @Override public void bind(@NonNull Sms model) {
    from.setText(model.phone.phoneNumber);
    date.setText(Converters.dateStringOf(model.time));
    text.setText(model.text);
  }
}
