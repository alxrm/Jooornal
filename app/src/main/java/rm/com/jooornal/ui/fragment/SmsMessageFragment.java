package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Sms;
import rm.com.jooornal.util.Converters;

/**
 * экран отображения СМС сообщения
 */
public final class SmsMessageFragment extends BaseFragment {

  private static final String KEY_SMS_MESSAGE = "KEY_SMS_MESSAGE";

  @BindView(R.id.sms_message_text) TextView messageText;

  private Sms message;

  /**
   * создание объекта экрана СМС сообщения
   *
   * @param message объект сообщения, содержащий данные
   * @return объект экрана
   */
  @NonNull public static SmsMessageFragment newInstance(@NonNull Sms message) {
    final Bundle args = new Bundle();
    final SmsMessageFragment fragment = new SmsMessageFragment();

    args.putParcelable(KEY_SMS_MESSAGE, message);
    fragment.setArguments(args);

    return fragment;
  }

  /**
   * создание интерфейса экрана
   *
   * @param inflater объект создания объекта интерфейса из XML вёрстки
   * @param container родительский объект интерфейса
   * @param savedInstanceState сохранённое состояние экрана(не используется)
   * @return объект созданного интерфейса
   */
  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_sms_message, container, false);
  }

  /**
   * интерфейс создан, привязка данных
   *
   * @param view корневой элемент, в котором отрисовываются элементы экрана
   * @param savedInstanceState сохранённое состояние, не используется здесь
   */
  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    messageText.setText(message.text);
  }

  /**
   * распаковка аргументов, переданных при создании экрана
   *
   * @param args сами параметры с пометкой, что они не пустые
   */
  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    message = args.getParcelable(KEY_SMS_MESSAGE);
  }

  /**
   * возвращает название экрана
   *
   * @return строку с датой получения сообщения
   */
  @NonNull @Override String getTitle() {
    return Converters.dateStringOf(message.time);
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
    return false;
  }
}
