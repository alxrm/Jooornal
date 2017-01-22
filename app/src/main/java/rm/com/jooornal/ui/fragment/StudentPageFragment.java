package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import java.util.Arrays;
import java.util.List;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.ui.MainActivity;
import rm.com.jooornal.ui.adapter.StudentPagerAdapter;
import rm.com.jooornal.util.Conditions;
import rm.com.jooornal.util.Converters;

import static rm.com.jooornal.constant.Navigation.STUDENT_PAGE_TITLES;

/**
 * Created by alex
 */
public final class StudentPageFragment extends BaseFragment {
  private static final String KEY_STUDENT = "KEY_STUDENT";

  @BindView(R.id.student_page_slider) ViewPager pager;

  private TabLayout tabs;
  private Student student;
  private List<BaseContentFragment> pages;

  public static StudentPageFragment newInstance(@NonNull Student student) {
    final Bundle args = new Bundle();
    final StudentPageFragment fragment = new StudentPageFragment();

    args.putParcelable(KEY_STUDENT, student);
    fragment.setArguments(args);

    return fragment;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_student_page, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupTabs();

    pager.setAdapter(getSectionsPagerAdapter());
    pager.setOffscreenPageLimit(3);

    tabs.setupWithViewPager(pager);
    setupTabTitles(tabs);
  }

  @Override public void onDestroy() {
    super.onDestroy();

    if (tabs != null) {
      tabs.setVisibility(View.GONE);
    }
  }

  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    student = args.getParcelable(KEY_STUDENT);
  }

  @NonNull @Override String getTitle() {
    return Converters.shortNameOf(student.surname, student.name, student.patronymic);
  }

  @Override boolean hasBackButton() {
    return true;
  }

  @Override boolean isNested() {
    return false;
  }

  private void setupTabs() {
    final MainActivity activity = (MainActivity) getActivity();
    Conditions.checkNotNull(activity, "Activity cannot be null");

    tabs = activity.getTabs();
    tabs.removeAllTabs();
    tabs.setVisibility(View.VISIBLE);
  }

  @NonNull private StudentPagerAdapter getSectionsPagerAdapter() {
    pages = Arrays.asList(StudentInfoFragment.newInstance(student),
        StudentMessagesFragment.newInstance(student.getSmsList()),
        StudentCallsFragment.newInstance(student.getCalls()));

    return new StudentPagerAdapter(getChildFragmentManager(), pages);
  }

  private void setupTabTitles(@NonNull TabLayout tabs) {
    for (int i = 0; i < tabs.getTabCount(); i++) {
      final TabLayout.Tab tab = tabs.getTabAt(i);

      if (tab != null) {
        tab.setText(STUDENT_PAGE_TITLES.get(i));
      }
    }
  }
}
