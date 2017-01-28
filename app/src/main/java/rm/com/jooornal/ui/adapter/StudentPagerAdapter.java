package rm.com.jooornal.ui.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.v13.app.FragmentPagerAdapter;
import java.util.List;
import rm.com.jooornal.ui.fragment.BaseContentFragment;

public final class StudentPagerAdapter extends FragmentPagerAdapter {

  private final List<BaseContentFragment> pages;

  public StudentPagerAdapter(@NonNull FragmentManager fm,
      @NonNull List<BaseContentFragment> pages) {
    super(fm);
    this.pages = pages;
  }

  @Override public Fragment getItem(int position) {
    return pages.get(position);
  }

  @Override public int getCount() {
    return pages.size();
  }
}
