package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindString;
import rm.com.jooornal.R;

/**
 * Created by alex
 */
public final class SettingsFragment extends BaseFragment {

  @BindString(R.string.page_name_settings) String title;

  public static SettingsFragment newInstance() {
    return new SettingsFragment();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_settings, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @NonNull @Override String getTitle() {
    return title;
  }

  @Override boolean hasBackButton() {
    return false;
  }
}
