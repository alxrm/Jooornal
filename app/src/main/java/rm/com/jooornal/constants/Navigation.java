package rm.com.jooornal.constants;

import java.util.HashMap;
import rm.com.jooornal.R;
import rm.com.jooornal.ui.fragments.BaseFragment;
import rm.com.jooornal.ui.fragments.NotesFragment;
import rm.com.jooornal.ui.fragments.SettingsFragment;
import rm.com.jooornal.ui.fragments.StudentsListFragment;

/**
 * Created by alex
 */

public interface Navigation {
  float ICON_MENU = 0F;
  float ICON_ARROW = 1F;

  HashMap<Integer, BaseFragment> PAGES = new HashMap<Integer, BaseFragment>(){{
    put(R.id.page_students, new StudentsListFragment());
    put(R.id.page_notes, new NotesFragment());
    put(R.id.page_settings, new SettingsFragment());
  }};
}
