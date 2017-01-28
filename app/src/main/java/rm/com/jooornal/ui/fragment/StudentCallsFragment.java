package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.data.entity.Call;
import rm.com.jooornal.ui.adapter.CallsListAdapter;

public final class StudentCallsFragment extends BaseContentFragment {
  private static final String KEY_CALLS_LIST = "KEY_CALLS_LIST";

  @Inject CallsListAdapter adapter;

  private ArrayList<Call> calls = new ArrayList<>();

  public static StudentCallsFragment newInstance(@NonNull List<Call> calls) {
    final Bundle args = new Bundle();
    final StudentCallsFragment fragment = new StudentCallsFragment();

    args.putParcelableArrayList(KEY_CALLS_LIST, new ArrayList<>(calls));
    fragment.setArguments(args);

    return fragment;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    adapter.updateData(calls);
    content.setAdapter(adapter);

    toggleContent(true, calls.isEmpty());
    add.hide();
  }

  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    calls = args.getParcelableArrayList(KEY_CALLS_LIST);
  }

  @Override protected void injectDependencies(@NonNull JooornalApplication app) {
    super.injectDependencies(app);
    app.injector().inject(this);
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
