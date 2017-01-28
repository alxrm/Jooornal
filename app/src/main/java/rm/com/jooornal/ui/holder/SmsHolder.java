package rm.com.jooornal.ui.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Sms;
import rm.com.jooornal.util.Converters;

public final class SmsHolder extends BaseHolder<Sms> {

  @BindView(R.id.item_message_from) TextView from;
  @BindView(R.id.item_message_date) TextView date;
  @BindView(R.id.item_message_text) TextView text;

  public SmsHolder(View itemView) {
    super(itemView);
  }

  @Override public void bind(@NonNull final Sms model) {
    from.setText(Converters.formatPhoneNumberOf(model.phone.phoneNumber));
    date.setText(Converters.timedDateStringOf(model.time));
    text.setText(model.text);

    itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (clickListener != null) {
          clickListener.onItemClick(model);
        }
      }
    });
  }
}
