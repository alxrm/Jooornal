package rm.com.jooornal.ui.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.v13.app.FragmentPagerAdapter;
import java.util.List;
import rm.com.jooornal.ui.fragment.BaseContentFragment;

/**
 * класс привязки экранов с информацией о студенте к вкладкам
 */
public final class StudentPagerAdapter extends FragmentPagerAdapter {

  private final List<BaseContentFragment> pages;

  /**
   * конструктор класса
   *
   * @param fm менеджер экранов
   * @param pages список экранов, которые нужно отрисовывать
   */
  public StudentPagerAdapter(@NonNull FragmentManager fm,
      @NonNull List<BaseContentFragment> pages) {
    super(fm);
    this.pages = pages;
  }

  /**
   * получение текущего экрана для отображения
   *
   * @param position позиция в списке экранов
   * @return объект экрана
   */
  @Override public Fragment getItem(int position) {
    return pages.get(position);
  }

  /**
   * получение количества экранов в списке
   *
   * @return число с количеством
   */
  @Override public int getCount() {
    return pages.size();
  }
}
