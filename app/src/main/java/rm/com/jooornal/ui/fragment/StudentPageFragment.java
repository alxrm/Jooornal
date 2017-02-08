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
 * экран с полной информацией о студенте
 */
public final class StudentPageFragment extends BaseFragment {
  private static final String KEY_STUDENT = "KEY_STUDENT";

  @BindView(R.id.student_page_slider) ViewPager pager;

  private TabLayout tabs;
  private Student student;

  /**
   * создание экрана с информацией о студенте
   *
   * @param student объект студента(данные)
   * @return объект экрана
   */
  public static StudentPageFragment newInstance(@NonNull Student student) {
    final Bundle args = new Bundle();
    final StudentPageFragment fragment = new StudentPageFragment();

    args.putParcelable(KEY_STUDENT, student);
    fragment.setArguments(args);

    return fragment;
  }

  /**
   * создание интерфейса экрана
   *
   * @param inflater объект создания объекта интерфейса из XML вёрстки
   * @param container родительский объект интерфейса
   * @param savedInstanceState сохранённое состояние экрана(не используется)
   * @return объект созданного интерфейса
   */
  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_student_page, container, false);
  }

  /**
   * интерфейс создан, привязка данных
   *
   * @param view корневой элемент, в котором отрисовываются элементы экрана
   * @param savedInstanceState сохранённое состояние, не используется здесь
   */
  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupTabs();

    pager.setAdapter(getSectionsPagerAdapter());
    pager.setOffscreenPageLimit(3);

    tabs.setupWithViewPager(pager);
    setupTabTitles(tabs);
  }

  /**
   * интерфейс отсоединён от контейнера, нужно очистить вкладки и спрятать их
   */
  @Override public void onDestroyView() {
    super.onDestroyView();
    toggleTabs(false);
  }

  /**
   * распаковка аргументов, переданных при создании экрана
   *
   * @param args сами параметры с пометкой, что они не пустые
   */
  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    student = args.getParcelable(KEY_STUDENT);
  }

  /**
   * получение заголовка экрана
   *
   * @return строка с заголовком
   */
  @NonNull @Override String getTitle() {
    return Converters.shortNameOf(student);
  }

  /**
   * есть ли в экране кнопка перехода назад в верхнем баре
   *
   * @return флаг наличия кнопки
   */
  @Override boolean hasBackButton() {
    return true;
  }

  /**
   * является ли экран вложенным
   *
   * @return флаг вложенности
   */
  @Override boolean isNested() {
    return false;
  }

  /**
   * показать/спрятать вкладки
   *
   * @param show флаг, отвечающий за показ
   */
  private void toggleTabs(boolean show) {
    if (tabs != null) {
      tabs.setVisibility(show ? View.VISIBLE : View.GONE);
    }
  }

  /**
   * заполнение вкладок
   */
  private void setupTabs() {
    final MainActivity activity = (MainActivity) getActivity();
    Conditions.checkNotNull(activity, "Activity cannot be null");

    tabs = activity.getTabs();
    tabs.removeAllTabs();
    tabs.setVisibility(View.VISIBLE);
  }

  /**
   * инициализация экранов, которые будут отображаться под вкладками
   *
   * @return обёртка над списком экранов
   */
  @NonNull private StudentPagerAdapter getSectionsPagerAdapter() {
    final List<BaseContentFragment> pages = Arrays.asList(StudentInfoFragment.newInstance(student),
        StudentMessagesFragment.newInstance(student.getSmsList()),
        StudentCallsFragment.newInstance(student.getCalls()));

    return new StudentPagerAdapter(getChildFragmentManager(), pages);
  }

  /**
   * инициализация названий вкладок
   *
   * @param tabs объект вкладок
   */
  private void setupTabTitles(@NonNull TabLayout tabs) {
    for (int i = 0; i < tabs.getTabCount(); i++) {
      final TabLayout.Tab tab = tabs.getTabAt(i);

      if (tab != null) {
        tab.setText(STUDENT_PAGE_TITLES.get(i));
      }
    }
  }
}
