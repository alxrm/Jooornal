package rm.com.jooornal.ui;

import android.support.annotation.NonNull;
import rm.com.jooornal.R;

/**
 * Created by alex
 */

public final class StudentsFragment extends BaseFragment {

  @NonNull @Override String getTitle() {
    return getActivity().getString(R.string.page_name_students);
  }

  @Override boolean hasBackButton() {
    return false;
  }
}
