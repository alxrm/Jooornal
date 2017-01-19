package rm.com.jooornal.ui.fragments;

import android.support.annotation.NonNull;
import rm.com.jooornal.R;

/**
 * Created by alex
 */
public final class NotesFragment extends BaseContentFragment {

  @NonNull @Override String getTitle() {
    return getActivity().getString(R.string.page_name_notes);
  }

  @Override boolean hasBackButton() {
    return false;
  }
}
