package rm.com.jooornal.ui;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.Unbinder;
import rm.com.jooornal.R;
import rm.com.jooornal.ui.fragment.BaseFragment;

/**
 * базовый класс с контейнером экранов
 */
public class BaseActivity extends AppCompatActivity {
  static String KEY_FRAGMENT_SAVE = "KEY_FRAGMENT_SAVE";

  protected BaseFragment currentFragment;
  protected Unbinder unbinder;

  /**
   * создание экрана
   *
   * @param savedInstanceState сохранённое состояние при повторном создании
   */
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  /**
   * метод, вызываемый для сохранения состояния перед уничтожением экрана(например поворот)
   *
   * @param outState объект, в который можно записать данные для сохранения
   */
  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    getFragmentManager().putFragment(outState, KEY_FRAGMENT_SAVE, currentFragment);
  }

  /**
   * нажата кнопка назад, сохраняется текущий экран
   */
  @Override public void onBackPressed() {
    super.onBackPressed();
    currentFragment = (BaseFragment) getFragmentManager().findFragmentById(R.id.container);
  }

  /**
   * уничтожение экрана, нужно очищаются все элементы интерфейса
   */
  @Override protected void onDestroy() {
    super.onDestroy();

    if (unbinder != null) {
      unbinder.unbind();
    }
  }

  /**
   * смена экрана
   *
   * @param fragment новый экран
   * @param isRoot является ли он корневым(есть ли что-то в истории перед ним)
   */
  final protected void changeFragment(@NonNull BaseFragment fragment, boolean isRoot) {
    this.currentFragment = fragment;

    final FragmentTransaction fragmentTransaction =
        getFragmentManager().beginTransaction().replace(R.id.container, fragment);

    if (!isRoot) {
      fragmentTransaction.addToBackStack(null);
    }

    fragmentTransaction.commit();
  }

  /**
   * изначальный экран, который отображается при входе в контейнер(который мог быть сохранён перед
   * при уничтожении контейнера)
   *
   * @param state данные экрана, которые могли быть сохранены
   * @param defaultFragment если в контейнере не было сохранённого фрагмента, создаётся новый
   */
  @NonNull final protected BaseFragment getInitialFragment(@Nullable Bundle state,
      @NonNull BaseFragment defaultFragment) {
    return (state == null) ? defaultFragment
        : (BaseFragment) getFragmentManager().getFragment(state, KEY_FRAGMENT_SAVE);
  }
}
