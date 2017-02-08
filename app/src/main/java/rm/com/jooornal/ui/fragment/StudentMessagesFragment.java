package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.data.entity.Sms;
import rm.com.jooornal.ui.adapter.SmsListAdapter;
import rm.com.jooornal.ui.holder.BaseHolder;

/**
 * экран со списком влодящих сообщений от студента
 */
public final class StudentMessagesFragment extends BaseContentFragment
    implements BaseHolder.OnClickListener<Sms> {

  private static final String KEY_MESSAGES_LIST = "KEY_MESSAGES_LIST";

  @Inject SmsListAdapter adapter;

  private ArrayList<Sms> messages = new ArrayList<>();

  /**
   * создание объекта экрана списка входящих сообщений
   *
   * @param smsList список сообщений(данные)
   * @return объект экрана
   */
  public static StudentMessagesFragment newInstance(@NonNull List<Sms> smsList) {
    final Bundle args = new Bundle();
    final StudentMessagesFragment fragment = new StudentMessagesFragment();

    args.putParcelableArrayList(KEY_MESSAGES_LIST, new ArrayList<>(smsList));
    fragment.setArguments(args);

    return fragment;
  }

  /**
   * интерфейс экрана создан, привязка к данным
   *
   * @param view корневой элемент, в котором отрисовываются элементы экрана
   * @param savedInstanceState сохранённое состояние, не используется здесь
   */
  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    adapter.updateData(messages);
    adapter.setOnClickListener(this);
    content.setAdapter(adapter);

    toggleContent(true, messages.isEmpty());
    add.hide();
  }

  /**
   * распаковка аргументов, переданных при создании экрана
   *
   * @param args сами параметры с пометкой, что они не пустые
   */
  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    messages = args.getParcelableArrayList(KEY_MESSAGES_LIST);
  }

  /**
   * внедрение зависимостей(например классы для запроса к настройкам)
   *
   * @param app объект приложения, через который можно вызвать контейнер зависимостей
   */
  @Override protected void injectDependencies(@NonNull JooornalApplication app) {
    super.injectDependencies(app);
    app.injector().inject(this);
  }

  /**
   * выбран элемент списка, откроется экран с отображением полного текста сообщения
   *
   * @param item сообщение
   */
  @Override public void onItemClick(@NonNull Sms item) {
    navigateTo(SmsMessageFragment.newInstance(item));
  }

  /**
   * получение заголовка экрана
   *
   * @return строка с заголовком
   */
  @NonNull @Override String getTitle() {
    return "";
  }

  /**
   * есть ли в экране кнопка перехода назад в верхнем баре
   *
   * @return флаг наличия кнопки
   */
  @Override boolean hasBackButton() {
    return false;
  }

  /**
   * является ли экран вложенным
   *
   * @return флаг вложенности
   */
  @Override boolean isNested() {
    return true;
  }
}
