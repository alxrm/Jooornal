package rm.com.jooornal.constant;

import java.util.HashMap;
import rm.com.jooornal.R;
import rm.com.jooornal.ui.fragment.BaseFragment;
import rm.com.jooornal.ui.fragment.NotesListFragment;
import rm.com.jooornal.ui.fragment.SettingsFragment;
import rm.com.jooornal.ui.fragment.StudentsListFragment;

/**
 * Created by alex
 */

public interface Navigation {
  float ICON_MENU = 0F;
  float ICON_ARROW = 1F;

  HashMap<Integer, BaseFragment> PAGES = new HashMap<Integer, BaseFragment>() {{
    put(R.id.page_students, StudentsListFragment.newInstance());
    put(R.id.page_notes, NotesListFragment.newInstance());
    put(R.id.page_settings, SettingsFragment.newInstance());
  }};
}
