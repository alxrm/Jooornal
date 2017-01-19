package rm.com.jooornal.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import rm.com.jooornal.R;

/**
 * Created by alex
 */
public final class SettingsFragment extends BaseFragment {

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_settings, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @NonNull @Override String getTitle() {
    return getActivity().getString(R.string.page_name_settings);
  }

  @Override boolean hasBackButton() {
    return false;
  }
}
