package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Sms;
import rm.com.jooornal.util.Converters;

public final class SmsMessageFragment extends BaseFragment {

  private static final String KEY_SMS_MESSAGE = "KEY_SMS_MESSAGE";

  @BindView(R.id.sms_message_text) TextView messageText;

  private Sms message;

  @NonNull public static SmsMessageFragment newInstance(@NonNull Sms message) {
    final Bundle args = new Bundle();
    final SmsMessageFragment fragment = new SmsMessageFragment();

    args.putParcelable(KEY_SMS_MESSAGE, message);
    fragment.setArguments(args);

    return fragment;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_sms_message, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    messageText.setText(message.text);
  }

  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    message = args.getParcelable(KEY_SMS_MESSAGE);
  }

  @NonNull @Override String getTitle() {
    return Converters.dateStringOf(message.time);
  }

  @Override boolean hasBackButton() {
    return true;
  }

  @Override boolean isNested() {
    return false;
  }
}
