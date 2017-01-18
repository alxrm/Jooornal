package rm.com.jooornal.ui;

import android.support.annotation.NonNull;
import rm.com.jooornal.R;

/**
 * Created by alex
 */
public final class SettingsFragment extends BaseFragment {

  @NonNull @Override String getTitle() {
    return getActivity().getString(R.string.page_name_settings);
  }

  @Override boolean hasBackButton() {
    return false;
  }
}
