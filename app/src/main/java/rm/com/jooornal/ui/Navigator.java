package rm.com.jooornal.ui;

import android.support.annotation.NonNull;
import rm.com.jooornal.ui.fragment.BaseFragment;

public interface Navigator {
  void navigateTo(@NonNull BaseFragment location);

  void navigateUp();

  void lockMenu(boolean should);

  boolean isMenuLocked();
}