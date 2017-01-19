package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindString;
import rm.com.jooornal.R;

/**
 * Created by alex
 */

public final class StudentCreateFragment extends BaseFragment {

  @BindString(R.string.page_name_student_create) String title;

  public static StudentCreateFragment newInstance() {
    return new StudentCreateFragment();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_student_create, container, false);
  }

  @NonNull @Override String getTitle() {
    return title;
  }

  @Override boolean hasBackButton() {
    return true;
  }
}
