package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.data.entity.Call;
import rm.com.jooornal.ui.adapter.CallsListAdapter;

/**
 * экран списка звонков от студента
 */
public final class StudentCallsFragment extends BaseContentFragment {
  private static final String KEY_CALLS_LIST = "KEY_CALLS_LIST";

  @Inject CallsListAdapter adapter;

  private ArrayList<Call> calls = new ArrayList<>();

  /**
   * создание объекта экрана списка звонков
   *
   * @param calls список звонков(данные)
   * @return объект экрана
   */
  public static StudentCallsFragment newInstance(@NonNull List<Call> calls) {
    final Bundle args = new Bundle();
    final StudentCallsFragment fragment = new StudentCallsFragment();

    args.putParcelableArrayList(KEY_CALLS_LIST, new ArrayList<>(calls));
    fragment.setArguments(args);

    return fragment;
  }

  /**
   * интерфейс создан, привязка данных
   *
   * @param view корневой элемент, в котором отрисовываются элементы экрана
   * @param savedInstanceState сохранённое состояние, не используется здесь
   */
  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    adapter.updateData(calls);
    content.setAdapter(adapter);

    toggleContent(true, calls.isEmpty());
    add.hide();
  }

  /**
   * распаковка аргументов, переданных при создании экрана
   *
   * @param args сами параметры с пометкой, что они не пустые
   */
  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    calls = args.getParcelableArrayList(KEY_CALLS_LIST);
  }

  /**
   * внедрение зависимостей
   *
   * @param app объект приложения, через который можно вызвать контейнер зависимостей
   */
  @Override protected void injectDependencies(@NonNull JooornalApplication app) {
    super.injectDependencies(app);
    app.injector().inject(this);
  }

  /**
   * возвращает название экрана
   *
   * @return строку с датой получения сообщения
   */
  @NonNull @Override String getTitle() {
    return "";
  }

  /**
   * возвращает флаг о наличии кнопки назад
   *
   * @return флаг наличия
   */
  @Override boolean hasBackButton() {
    return false;
  }

  /**
   * возвращает флаг, является ли экран вложенным
   *
   * @return флаг вложенности
   */
  @Override boolean isNested() {
    return true;
  }
}
