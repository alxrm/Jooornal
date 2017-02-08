package rm.com.jooornal.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import rm.com.jooornal.R;
import rm.com.jooornal.ui.fragment.BaseFragment;
import rm.com.jooornal.util.Conditions;

import static android.support.v4.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
import static android.support.v4.widget.DrawerLayout.LOCK_MODE_UNLOCKED;
import static rm.com.jooornal.constant.Navigation.ICON_ARROW;
import static rm.com.jooornal.constant.Navigation.ICON_MENU;
import static rm.com.jooornal.constant.Navigation.PAGES;

/**
 * главный контейнер, здесь происходит смена экранов
 */
public final class MainActivity extends BaseActivity
    implements Navigator, NavigationView.OnNavigationItemSelectedListener {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.tabs) TabLayout tabs;
  @BindView(R.id.drawer) NavigationView drawer;
  @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;

  private DrawerArrowDrawable navigationIcon;

  /**
   * инициализаия главного контейнера
   *
   * @param savedInstanceState сохранённое состояние при повторном создании
   */
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    unbinder = ButterKnife.bind(this);
    currentFragment = getInitialFragment(savedInstanceState, PAGES.get(R.id.page_students));

    setSupportActionBar(toolbar);
    setupNavigationIcon(toolbar);

    drawer.setNavigationItemSelectedListener(this);

    changeFragment(currentFragment, true);
  }

  /**
   * переход к другому экрану
   *
   * @param fragment следующий экран
   */
  @Override public void navigateTo(@NonNull BaseFragment fragment) {
    Conditions.checkNotNull(fragment, "Fragment cannot be null");
    changeFragment(fragment, false);
  }

  /**
   * переход назад
   */
  @Override public void navigateUp() {
    if (isMenuLocked()) {
      onBackPressed();
    } else {
      drawerLayout.openDrawer(GravityCompat.START);
    }
  }

  /**
   * (раз)блокировка бокового меню
   *
   * @param should флаг, обозначающий блокировать или разблокировать меню
   */
  @Override public void lockMenu(boolean should) {
    final float navigationIconState = should ? ICON_ARROW : ICON_MENU;
    final int lockMode = should ? LOCK_MODE_LOCKED_CLOSED : LOCK_MODE_UNLOCKED;

    navigationIcon.setProgress(navigationIconState);
    drawerLayout.setDrawerLockMode(lockMode, GravityCompat.START);
  }

  /**
   * проверка, заблокировано ли боковое меню
   *
   * @return флаг блокировки меню
   */
  @Override public boolean isMenuLocked() {
    return drawerLayout.getDrawerLockMode(GravityCompat.START) == LOCK_MODE_LOCKED_CLOSED;
  }

  /**
   * нажата кнопка назад, если боковое меню открыто, оно закроется, если нет, будет переход к
   * предыдущему экрану
   */
  @Override public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawers();
    } else {
      super.onBackPressed();
    }
  }

  /**
   * выбран элемент в боковом меню, закрывается боковое меню и открывается другой экран
   *
   * @param item элемент меню
   * @return возвращает флаг, обработано ли вобытие выбора
   */
  @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    final BaseFragment next = PAGES.get(item.getItemId());
    drawerLayout.closeDrawers();
    changeFragment(next, true);
    return true;
  }

  /**
   * в верхний бар добавляется иконка для вызова бокового меню или для перехода назад
   *
   * @param toolbar объект верхнего бара
   */
  private void setupNavigationIcon(@NonNull Toolbar toolbar) {
    navigationIcon = new DrawerArrowDrawable(this);
    navigationIcon.setColor(Color.WHITE);
    toolbar.setNavigationIcon(navigationIcon);
  }

  /**
   * возвращает контейнер для вкладок по запросу(в некоторых экранах могут быть вкладки)
   *
   * @return объект контейнера
   */
  @NonNull public TabLayout getTabs() {
    Conditions.checkNotNull(tabs, "Tabs cannot be null");
    return tabs;
  }
}