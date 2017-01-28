package rm.com.jooornal.constant;

import android.support.v4.util.SparseArrayCompat;
import rm.com.jooornal.R;

public interface StudentInfo {
  int ENTRY_TITLE = 1;
  int ENTRY_TEXT = 2;

  SparseArrayCompat<Integer> ENTRY_LAYOUTS = new SparseArrayCompat<Integer>(2) {{
    put(ENTRY_TITLE, R.layout.item_info_entry_title);
    put(ENTRY_TEXT, R.layout.item_info_entry_text);
  }};
}
