package rm.com.jooornal;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rm.com.jooornal.constant.Navigation;
import rm.com.jooornal.ui.Navigator;
import rm.com.jooornal.ui.fragment.BaseFragment;
import rm.com.jooornal.util.Conditions;

/**
 * главный контейнер, здесь происходит смена экранов
 */
public final class MainActivity extends AppCompatActivity
    implements Navigator, NavigationView.OnNavigationItemSelectedListener {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.drawer) NavigationView drawer;
  @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;

  private DrawerArrowDrawable navigationIcon;
  private Unbinder unbinder;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    unbinder = ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    setupNavigationIcon(toolbar);

    drawer.setNavigationItemSelectedListener(this);
    changeFragment(Navigation.PAGES.get(R.id.page_students), true);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
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
    final float navigationIconState = should ? Navigation.ICON_ARROW : Navigation.ICON_MENU;
    final int lockMode =
        should ? DrawerLayout.LOCK_MODE_LOCKED_CLOSED : DrawerLayout.LOCK_MODE_UNLOCKED;

    navigationIcon.setProgress(navigationIconState);
    drawerLayout.setDrawerLockMode(lockMode, GravityCompat.START);
  }

  @Override public boolean isMenuLocked() {
    return drawerLayout.getDrawerLockMode(GravityCompat.START)
        == DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
  }

  @Override public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawers();
    } else {
      super.onBackPressed();
    }
  }

  @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    final BaseFragment next = Navigation.PAGES.get(item.getItemId());
    drawerLayout.closeDrawers();
    changeFragment(next, true);
    return true;
  }

  private void setupNavigationIcon(@NonNull Toolbar toolbar) {
    navigationIcon = new DrawerArrowDrawable(this);
    navigationIcon.setColor(Color.WHITE);
    toolbar.setNavigationIcon(navigationIcon);
  }

  private void changeFragment(@NonNull BaseFragment fragment, boolean isRoot) {
    final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction()
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .replace(R.id.container, fragment);

    if (!isRoot) {
      fragmentTransaction.addToBackStack(null);
    }

    fragmentTransaction.commit();
  }
}