package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rm.com.jooornal.data.entity.Sms;
import rm.com.jooornal.ui.adapter.SmsListAdapter;
import rm.com.jooornal.ui.holder.BaseHolder;

/**
 * Created by alex
 */
public final class StudentMessagesFragment extends BaseContentFragment
    implements BaseHolder.OnClickListener<Sms> {

  private static final String KEY_MESSAGES_LIST = "KEY_MESSAGES_LIST";

  @Inject SmsListAdapter adapter;

  private ArrayList<Sms> messages;

  public static StudentMessagesFragment newInstance(@NonNull List<Sms> smsList) {
    final Bundle args = new Bundle();
    final StudentMessagesFragment fragment = new StudentMessagesFragment();

    args.putParcelableArrayList(KEY_MESSAGES_LIST, new ArrayList<>(smsList));
    fragment.setArguments(args);

    return fragment;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    add.setVisibility(View.GONE);

    adapter.updateData(messages);
    adapter.setOnClickListener(this);
    content.setAdapter(adapter);

    toggleContent(true);
  }

  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    messages = args.getParcelableArrayList(KEY_MESSAGES_LIST);
  }

  @Override public void onItemClick(@NonNull Sms item) {
    navigateTo(SmsMessageFragment.newInstance(item));
  }

  @NonNull @Override String getTitle() {
    return "";
  }

  @Override boolean hasBackButton() {
    return false;
  }

  @Override boolean isNested() {
    return true;
  }
}
