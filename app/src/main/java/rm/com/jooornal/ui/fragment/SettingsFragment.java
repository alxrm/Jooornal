package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import java.util.Calendar;
import java.util.Locale;
import javax.inject.Inject;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.R;
import rm.com.jooornal.data.preferences.BooleanPreference;
import rm.com.jooornal.data.provider.DeleteOldNotesProvider;
import rm.com.jooornal.data.provider.ProviderListener;
import rm.com.jooornal.inject.qualifiers.BirthdayNotifications;
import rm.com.jooornal.inject.qualifiers.NoteNotifications;

/**
 * экран настроек
 */
public final class SettingsFragment extends BaseFragment implements ProviderListener<Integer> {

  @BindString(R.string.page_name_settings) String title;
  @BindString(R.string.ask_delete_old_notes) String askMessageDeleteOldNotes;

  @BindView(R.id.settings_notifications_notes) SwitchCompat noteNotificationsView;
  @BindView(R.id.settings_notifications_birthday) SwitchCompat birthdayNotificationsView;

  @Inject DeleteOldNotesProvider deleteNotesProvider;
  @Inject @NoteNotifications BooleanPreference noteNotificationsPreference;
  @Inject @BirthdayNotifications BooleanPreference birthdayNotificationsPreference;

  /**
   * создание объекта экрана настроек
   *
   * @return объект экрана
   */
  public static SettingsFragment newInstance() {
    return new SettingsFragment();
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
    return inflater.inflate(R.layout.fragment_settings, container, false);
  }

  /**
   * интерфейс создан, привязка данных
   *
   * @param view корневой элемент, в котором отрисовываются элементы экрана
   * @param savedInstanceState сохранённое состояние, не используется здесь
   */
  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    noteNotificationsView.setChecked(noteNotificationsPreference.get());
    birthdayNotificationsView.setChecked(birthdayNotificationsPreference.get());
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
   * результат провайдера, отвечающего за удаление старых заметок
   *
   * @param payload количество удалённых заметок
   */
  @Override public void onProviderResult(@NonNull Integer payload) {
    final String message = String.format(Locale.getDefault(), "Заметок удалено: %d", payload);

    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }

  /**
   * удалить старые заметки, выполнится после подтверждения в диалоге
   */
  @OnClick(R.id.settings_clear_old_notes) final void onClearOldNotes() {
    ask(askMessageDeleteOldNotes, new OnAskListener() {
      @Override public void onAction() {
        final Calendar currentDateCalendar = Calendar.getInstance();
        currentDateCalendar.set(Calendar.MINUTE, 0);
        currentDateCalendar.set(Calendar.HOUR_OF_DAY, 0);

        deleteNotesProvider.provide(currentDateCalendar.getTimeInMillis(), SettingsFragment.this);
      }
    });
  }

  /**
   * вызов переключателя, отвечающего за настройку уведомлений о заметках
   */
  @OnCheckedChanged(R.id.settings_notifications_notes) final void onNotificationsNoteChanged() {
    final boolean newValue = noteNotificationsView.isChecked();
    final boolean oldValue = noteNotificationsPreference.get();

    if (oldValue != newValue) {
      noteNotificationsPreference.set(newValue);
    }
  }

  /**
   * вызов переключателя, отвечающего за настройку уведомлений о днях рождениях
   */
  @OnCheckedChanged(R.id.settings_notifications_birthday)
  final void onNotificationsBirthdayChanged() {
    final boolean newValue = birthdayNotificationsView.isChecked();
    final boolean oldValue = birthdayNotificationsPreference.get();

    if (oldValue != newValue) {
      birthdayNotificationsPreference.set(newValue);
    }
  }

  /**
   * нажатие на элемент с переключателем уведомлений о заметках
   */
  @OnClick(R.id.settings_notifications_notes_wrapper) final void onNotificationsNoteToggle() {
    final boolean oldValue = noteNotificationsView.isChecked();
    noteNotificationsView.setChecked(!oldValue);
    onNotificationsNoteChanged();
  }

  /**
   * нажатие на элемент с переключателем уведомлений о днях рождениях
   */
  @OnClick(R.id.settings_notifications_birthday_wrapper)
  final void onNotificationsBirthdayToggle() {
    final boolean oldValue = birthdayNotificationsView.isChecked();
    birthdayNotificationsView.setChecked(!oldValue);
    onNotificationsBirthdayChanged();
  }

  /**
   * возвращает название экрана
   *
   * @return строка с названием
   */
  @NonNull @Override public String getTitle() {
    return title;
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
