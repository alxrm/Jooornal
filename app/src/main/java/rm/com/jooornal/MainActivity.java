package rm.com.jooornal;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import rm.com.jooornal.ui.BaseFragment;
import rm.com.jooornal.ui.Navigator;

/**
 * главный контейнер, здесь происходит смена экранов
 */
public final class MainActivity extends AppCompatActivity implements Navigator {

  /**
   * метод, вызываемый при создании контейнера, здесь выполняется необходимая инициализация
   *
   * @param savedInstanceState сохранённое состояние, не используется здесь
   */
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //navigateTo(CatalogFragment.newInstance(), true);
  }

  /**
   * метод перехода в новый экран с возможностью вернуться назад
   *
   * @param fragment экземпляр экрана, на который переходит пользователь
   */
  @Override public void navigateTo(@NonNull BaseFragment fragment) {
    navigateTo(fragment, false);
  }

  @Override public void navigateBack() {
    onBackPressed();
  }

  /**
   * перегруженная версия метода перехода к новому экрану, с возможностью указания, что экран
   * начальный, если пользователь выйдет из него, то он покинет приложение
   *
   * @param fragment экземпляр экрана, на который переходит пользователь
   * @param root флаг, определяющий, начальный ли это экран
   */
  private void navigateTo(@NonNull BaseFragment fragment, boolean root) {
    final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction()
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .replace(R.id.root, fragment);

    if (!root) {
      fragmentTransaction.addToBackStack(null);
    }

    fragmentTransaction.commit();
  }

}