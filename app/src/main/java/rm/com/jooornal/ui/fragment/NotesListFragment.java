package rm.com.jooornal.ui.fragment;

import android.support.annotation.NonNull;
import butterknife.BindString;
import rm.com.jooornal.R;

/**
 * Created by alex
 */
public final class NotesListFragment extends BaseContentFragment {

  @BindString(R.string.page_name_notes) String title;

  public static NotesListFragment newInstance() {
    return new NotesListFragment();
  }

  @NonNull @Override String getTitle() {
    return title;
  }

  @Override boolean hasBackButton() {
    return false;
  }

}
