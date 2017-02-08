package rm.com.jooornal.ui;

import android.support.annotation.NonNull;
import rm.com.jooornal.ui.fragment.BaseFragment;

/**
 * обёртка для вызова переходов между экранами
 */
public interface Navigator {

  /**
   * метод для перехода к другому экрану
   *
   * @param location следующий экран
   */
  void navigateTo(@NonNull BaseFragment location);

  /**
   * переход назад
   */
  void navigateUp();

  /**
   * (раз)блокировка бокового меню
   *
   * @param should флаг, обозначающий блокировать или разблокировать меню
   */
  void lockMenu(boolean should);

  /**
   * возвращает флаг, заблокировано ли боковое меню
   *
   * @return флаг блокировки меню
   */
  boolean isMenuLocked();
}