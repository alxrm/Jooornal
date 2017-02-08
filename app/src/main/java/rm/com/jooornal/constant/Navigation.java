package rm.com.jooornal.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import rm.com.jooornal.R;
import rm.com.jooornal.ui.fragment.BaseFragment;
import rm.com.jooornal.ui.fragment.NotesListFragment;
import rm.com.jooornal.ui.fragment.SettingsFragment;
import rm.com.jooornal.ui.fragment.StudentsListFragment;

/**
 * константы, связанные с навигацией
 */
public interface Navigation {
  float ICON_MENU = 0F;
  float ICON_ARROW = 1F;

  HashMap<Integer, BaseFragment> PAGES = new HashMap<Integer, BaseFragment>(3) {{
    put(R.id.page_students, StudentsListFragment.newInstance());
    put(R.id.page_notes, NotesListFragment.newInstance());
    put(R.id.page_settings, SettingsFragment.newInstance());
  }};

  List<String> STUDENT_PAGE_TITLES = Arrays.asList("Инфо", "СМС", "Звонки");
}
