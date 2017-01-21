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
 * Created by alex
 */

public class BaseActivity extends AppCompatActivity {
  static String KEY_FRAGMENT_SAVE = "KEY_FRAGMENT_SAVE";

  protected BaseFragment currentFragment;
  protected Unbinder unbinder;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    getFragmentManager().putFragment(outState, KEY_FRAGMENT_SAVE, currentFragment);
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    currentFragment = (BaseFragment) getFragmentManager().findFragmentById(R.id.container);
  }

  @Override protected void onDestroy() {
    super.onDestroy();

    if (unbinder != null) {
      unbinder.unbind();
    }
  }

  final protected void changeFragment(@NonNull BaseFragment fragment, boolean isRoot) {
    this.currentFragment = fragment;

    final FragmentTransaction fragmentTransaction =
        getFragmentManager().beginTransaction().replace(R.id.container, fragment);

    if (!isRoot) {
      fragmentTransaction.addToBackStack(null);
    }

    fragmentTransaction.commit();
  }

  @NonNull final protected BaseFragment getInitialFragment(@Nullable Bundle state,
      @NonNull BaseFragment defaultFragment) {
    return (state == null) ? defaultFragment
        : (BaseFragment) getFragmentManager().getFragment(state, KEY_FRAGMENT_SAVE);
  }
}
