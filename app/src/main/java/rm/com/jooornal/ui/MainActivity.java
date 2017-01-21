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

  @Override public void navigateTo(@NonNull BaseFragment fragment) {
    Conditions.checkNotNull(fragment, "Fragment cannot be null");
    changeFragment(fragment, false);
  }

  @Override public void navigateUp() {
    if (isMenuLocked()) {
      onBackPressed();
    } else {
      drawerLayout.openDrawer(GravityCompat.START);
    }
  }

  @Override public void lockMenu(boolean should) {
    final float navigationIconState = should ? ICON_ARROW : ICON_MENU;
    final int lockMode = should ? LOCK_MODE_LOCKED_CLOSED : LOCK_MODE_UNLOCKED;

    navigationIcon.setProgress(navigationIconState);
    drawerLayout.setDrawerLockMode(lockMode, GravityCompat.START);
  }

  @Override public boolean isMenuLocked() {
    return drawerLayout.getDrawerLockMode(GravityCompat.START) == LOCK_MODE_LOCKED_CLOSED;
  }

  @Override public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawers();
    } else {
      super.onBackPressed();
    }
  }

  @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    final BaseFragment next = PAGES.get(item.getItemId());
    drawerLayout.closeDrawers();
    changeFragment(next, true);
    return true;
  }

  private void setupNavigationIcon(@NonNull Toolbar toolbar) {
    navigationIcon = new DrawerArrowDrawable(this);
    navigationIcon.setColor(Color.WHITE);
    toolbar.setNavigationIcon(navigationIcon);
  }

  @NonNull public TabLayout getTabs() {
    Conditions.checkNotNull(tabs, "Tabs cannot be null");
    return tabs;
  }
}