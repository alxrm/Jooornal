package rm.com.jooornal.ui.fragment;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.canelmas.let.DeniedPermission;
import com.canelmas.let.Let;
import com.canelmas.let.RuntimePermissionListener;
import com.canelmas.let.RuntimePermissionRequest;
import java.util.List;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.R;
import rm.com.jooornal.ui.Navigator;

/**
 * базовый класс экрана(фрагмента) приложения, отрисовывающий данные и индикатор загрузки, на
 * случай, если данные ещё не получены
 */
public abstract class BaseFragment extends Fragment implements RuntimePermissionListener {

  @BindString(R.string.message_permission_needed) String messagePermissionNeeded;

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

    final JooornalApplication app = getApplication();
    final Bundle args = getArguments();

    if (args != null) {
      unwrapArguments(args);
    }

    if (app != null) {
      injectDependencies(app);
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
   * метод, вызываемый после запроса прав доступа
   *
   * @param requestCode код запроса на разрешение
   * @param permissions массив названий прав доступа
   * @param grantResults результаты запроса
   */
  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    Let.handle(this, requestCode, permissions, grantResults);
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
    if (!isNested()) {
      updateAppbar(getTitle());
      lockMenu(hasBackButton());
    }
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
   * метод, нужный для того, чтобы добавлять пояснения к диалогу запроса прав доступа
   *
   * @param permissionList список прав доступа
   * @param permissionRequest запрос на права
   */
  @Override public void onShowPermissionRationale(List<String> permissionList,
      RuntimePermissionRequest permissionRequest) {
  }

  /**
   * разрешение не дано
   *
   * @param deniedPermissionList список прав доступа, разрешение на которые не получено
   */
  @Override public void onPermissionDenied(List<DeniedPermission> deniedPermissionList) {
    Toast.makeText(getActivity(), messagePermissionNeeded, Toast.LENGTH_LONG).show();
  }

  /**
   * абстрактный метод, который должен быть переопределён дочерними экранами, нужен для получения
   * заголовка экрана
   *
   * @return строка, содержащая заголовок
   */
  @NonNull abstract String getTitle();

  /**
   * абстрактный метод, который должен быть переопределён дочерними экранами, нужен для того, чтобы
   * знать, должна ли быть в верхнем баре кнопка перехода назад(стрелка влево)
   *
   * @return {@code true} если кнопка должна быть
   */
  abstract boolean hasBackButton();

  /**
   * абстрактный метод, который помечает, является ли экран вложенным
   *
   * @return флаг вложенности
   */
  abstract boolean isNested();

  /**
   * метод вызываемый при распаковке параметров
   *
   * @param args сами параметры с пометкой, что они не пустые
   */
  protected void unwrapArguments(@NonNull Bundle args) {
  }

  /**
   * метод вызываемый при необходимости внедрить зависимости
   *
   * @param app объект приложения, через который можно вызвать контейнер зависимостей
   */
  protected void injectDependencies(@NonNull JooornalApplication app) {
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

  /**
   * метод получения объекта приложения
   *
   * @return объект приложения
   */
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

  /**
   * метод блокировки бокового меню
   *
   * @param should флаг блокировки
   */
  final protected void lockMenu(boolean should) {
    final Navigator navigator = getNavigator();

    if (navigator != null) {
      navigator.lockMenu(should);
    }
  }

  /**
   * метод для создания диалога с запросом на подтверждение действия
   *
   * @param message сообщение с вопросом
   * @param action действие, которое выполнится после подтверждения
   */
  final protected void ask(String message, final OnAskListener action) {
    new AlertDialog.Builder(getActivity()).setMessage(message)
        .setNegativeButton(R.string.ask_negative, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        })
        .setPositiveButton(R.string.ask_positive, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            action.onAction();
          }
        })
        .create()
        .show();
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

  /**
   * действие, которое выполнится после запроса на подтверждение
   */
  protected interface OnAskListener {
    void onAction();
  }
}