package rm.com.jooornal.ui.fragments;

import android.support.annotation.NonNull;
import rm.com.jooornal.R;

/**
 * Created by alex
 */

public final class StudentsListFragment extends BaseContentFragment {

  @NonNull @Override String getTitle() {
    return getActivity().getString(R.string.page_name_students);
  }

  @Override boolean hasBackButton() {
    return false;
  }
}
