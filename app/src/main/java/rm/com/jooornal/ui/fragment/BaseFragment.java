package rm.com.jooornal.ui.fragment;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.ui.Navigator;

/**
 * базовый класс экрана(фрагмента) приложения, отрисовывающий данные и индикатор загрузки, на
 * случай, если данные ещё не получены
 */
public abstract class BaseFragment extends Fragment {

  protected Unbinder unbinder;

  /**
   * метод, вызываемый при создании экрана
   *
   * здесь выставляется параметр, что у экрана может быть меню(поиск — часть меню)
   * также здесь распаковываются параметры, которые могли быть переданы при создании экрана
   *
   * @param savedInstanceState сохранённое состояние, не используется здесь
   */
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);

    final Bundle args = getArguments();

    if (args != null) {
      unwrapArguments(args);
    }
  }

  /**
   * метод, вызываемый в момент, когда визульная часть уже создана, здесь происходит инициализация
   * индикатора загрузки и элемента интерфейса для отображения данных
   *
   * @param view корневой элемент, в котором отрисовываются элементы экрана
   * @param savedInstanceState сохранённое состояние, не используется здесь
   */
  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    unbinder = ButterKnife.bind(this, view);
  }

  /**
   * метод, вызываемый при выборе элемента меню
   *
   * @param item выбранный элемент
   * @return возвращает флаг, {@code true} если событие обработано или {@code false} в противном
   * случае. {@code super.onOptionsItemSelected(item)} возвращает {@code false}, он вызывается,
   * когда ни один из элементов не был выбран
   */
  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      navigateUp();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  /**
   * метод, вызываемый в момент, когда работа с экраном возобновляется
   * в этом методе обновляется заголовок в верхнем баре
   */
  @Override public void onResume() {
    super.onResume();
    updateAppbar(getTitle());
    lockMenu(hasBackButton());
  }

  /**
   * метод, вызываемый при уничтожении экрана, здесь элементы интерфейса деинициализируются, чтобы
   * не засорять память
   */
  @Override public void onDestroy() {
    super.onDestroy();
    if (unbinder != null) {
      unbinder.unbind();
    }
  }

  /**
   * абстрактный метод, который должен быть переопределён дочерними экранами, нужен для получения
   * заголовка экрана
   *
   * @return строка, содержащая заголовок
   */
  @NonNull public abstract String getTitle();

  /**
   * абстрактный метод, который должен быть переопределён дочерними экранами, нужен для того, чтобы
   * знать, должна ли быть в верхнем баре кнопка перехода назад(стрелка влево)
   *
   * @return {@code true} если кнопка должна быть
   */
  abstract boolean hasBackButton();

  /**
   * метод вызываемый при распаковке параметров
   *
   * @param args сами параметры с пометкой, что они не пустые
   */
  protected void unwrapArguments(@NonNull Bundle args) {
  }

  /**
   * метод, эмулирующий нажатие системной кнопки назад
   */
  protected void navigateUp() {
    final Navigator owner = getNavigator();

    if (owner != null) {
      owner.navigateUp();
    }
  }

  /**
   * метод получения контейнера, где отрисовываются окна
   *
   * @return экземпляр контейнера
   */
  @Nullable final protected Navigator getNavigator() {
    return (Navigator) getActivity();
  }

  @Nullable final protected JooornalApplication getApplication() {
    final Activity owner = getActivity();
    final Application app = owner != null ? owner.getApplication() : null;

    return ((JooornalApplication) app);
  }

  /**
   * метод, для открытия другого экрана
   *
   * @param fragment экземпляр окна, которое нужно открыть
   */
  final protected void navigateTo(@NonNull BaseFragment fragment) {
    final Navigator navigator = getNavigator();

    if (navigator != null) {
      navigator.navigateTo(fragment);
    }
  }

  final protected void lockMenu(boolean should) {
    final Navigator navigator = getNavigator();

    if (navigator != null) {
      navigator.lockMenu(should);
    }
  }

  /**
   * метод обновляющий параметры верхнего бара экрана(наличие кнопки назад и заголовок)
   */
  final void updateAppbar(@NonNull String title) {
    final AppCompatActivity owner = (AppCompatActivity) getActivity();
    final ActionBar supportBar = owner != null ? owner.getSupportActionBar() : null;

    if (supportBar != null) {
      supportBar.setTitle(title);
    }
  }
}