package rm.com.jooornal.ui.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import com.canelmas.let.AskPermission;
import com.canelmas.let.DeniedPermission;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Note;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.inject.qualifiers.NoteNotifications;
import rm.com.jooornal.util.Converters;
import rm.com.jooornal.util.Events;

import static rm.com.jooornal.constant.Events.EVENT_NULL_ID;

/**
 * экран заметки(текущей или новой)
 */
public final class NoteFragment extends BaseFormFragment
    implements StudentPickerFragment.OnStudentPickerListener, DatePickerDialog.OnDateSetListener {

  private static final String KEY_NOTE = "KEY_NOTE";

  @BindString(R.string.page_name_note_page) String pageTitle;
  @BindString(R.string.ask_deassign_student) String askMessageDeassignStudent;
  @BindString(R.string.ask_deassign_date) String askMessageDeassignDate;
  @BindString(R.string.message_note_ignored) String messageNoteIgnored;
  @BindString(R.string.message_note_no_text) String messageNoteNoText;
  @BindString(R.string.note_event_title_stub) String eventTitleStub;

  @BindView(R.id.note_create_student) TextView student;
  @BindView(R.id.note_create_time) TextView dueDate;
  @BindView(R.id.note_create_text) EditText text;
  @BindView(R.id.note_create_title) EditText title;

  @Inject ContentResolver contentResolver;
  @Inject @NoteNotifications Provider<Boolean> shouldNotify;

  private Note note = new Note();
  private boolean hasCalendarPermission = false;

  /**
   * создание экрана новой заметки
   *
   * @return объект экрана
   */
  @NonNull public static NoteFragment newInstance() {
    return new NoteFragment();
  }

  /**
   * создание экрана существующей заметки
   *
   * @param note объект с данными заметки
   * @return объект экрана
   */
  @NonNull public static NoteFragment newInstance(@NonNull Note note) {
    final Bundle args = new Bundle();
    final NoteFragment fragment = new NoteFragment();

    args.putParcelable(KEY_NOTE, note);
    fragment.setArguments(args);

    return fragment;
  }

  /**
   * создание экрана, в нём происходит запрос на получение прав доступа
   *
   * @param savedInstanceState сохранённое состояние, не используется здесь
   */
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    askAndSavePermission();
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
    return inflater.inflate(R.layout.fragment_note_page, container, false);
  }

  /**
   * интерфейс экрана создан, привязка к данным
   *
   * @param view корневой элемент, в котором отрисовываются элементы экрана
   * @param savedInstanceState сохранённое состояние, не используется здесь
   */
  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    title.setText(note.name);
    text.setText(note.text);

    if (note.due != 0L) {
      dueDate.setVisibility(View.VISIBLE);
      dueDate.setText(Converters.dateStringOf(note.due));
    }

    if (note.student != null) {
      student.setVisibility(View.VISIBLE);
      student.setText(Converters.shortNameOf(note.student));
    }
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
   * в доступе отказано, выводится всплывающее сообщение с пояснением
   *
   * @param deniedPermissionList список прав доступа, разрешение на которые не получено
   */
  @Override public void onPermissionDenied(List<DeniedPermission> deniedPermissionList) {
    Toast.makeText(getActivity(), messageNoteIgnored, Toast.LENGTH_LONG).show();
  }

  /**
   * распаковка аргументов, переданных при создании экрана
   *
   * @param args сами параметры с пометкой, что они не пустые
   */
  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    note = args.getParcelable(KEY_NOTE);
  }

  /**
   * изменён текст заметки
   *
   * @param text строка с текстом
   */
  @OnTextChanged(R.id.note_create_text)
  final void onNoteTextChanged(@NonNull CharSequence text) {
    note.text = text.toString();
  }

  /**
   * изменён заголовок заметки
   *
   * @param title строка с заголовком
   */
  @OnTextChanged(R.id.note_create_title)
  final void onNoteTitleChanged(@NonNull CharSequence title) {
    note.name = title.toString();
  }

  /**
   * удалить привязку к студенту, вызывается диалог для подтверждения действия
   */
  @OnClick(R.id.note_create_student) final void onDeleteStudent() {
    ask(askMessageDeassignStudent, new OnAskListener() {
      @Override public void onAction() {
        note.student = null;
        student.setVisibility(View.GONE);
      }
    });
  }

  /**
   * удалить привязку к дате, вызывается диалог для подтверждения действия, также удаляется событие
   * из календаря, если оно было
   */
  @OnClick(R.id.note_create_time) final void onDeleteDate() {
    ask(askMessageDeassignDate, new OnAskListener() {
      @Override public void onAction() {
        note.due = 0L;
        dueDate.setVisibility(View.GONE);

        if (note.noteEventId != EVENT_NULL_ID) {
          Events.deleteCalendarEvent(contentResolver, note.noteEventId);
          note.noteEventId = EVENT_NULL_ID;
        }
      }
    });
  }

  /**
   * добавление привязки к студенту, открывается новый экран со списком студентов
   */
  @OnClick(R.id.note_create_assign_student) final void onAssignStudent() {
    final StudentPickerFragment pickerFragment = StudentPickerFragment.newInstance();
    pickerFragment.setPickerListener(this);

    navigateTo(pickerFragment);
  }

  /**
   * выбран студент в другом экране
   *
   * @param picked объект выбранного студента
   */
  @Override public void onStudentPicked(@NonNull Student picked) {
    note.student = picked;
    student.setVisibility(View.VISIBLE);
    student.setText(Converters.shortNameOf(picked));
  }

  /**
   * добавление привязки заметки к дате, открывается диалог с выбором даты
   */
  @OnClick(R.id.note_create_assign_time) final void onAssignDueDate() {
    final Calendar birth = Calendar.getInstance();
    final DatePickerDialog dpd =
        DatePickerDialog.newInstance(this, birth.get(Calendar.YEAR), birth.get(Calendar.MONTH),
            birth.get(Calendar.DAY_OF_MONTH));

    dpd.show(getFragmentManager(), "dpd");
  }

  /**
   * выбрана дата уведомления в диалоге
   *
   * @param view элемент интерфейса с диалогом
   * @param year номер года
   * @param monthOfYear месяц
   * @param dayOfMonth день
   */
  @Override public void onDateSet(DatePickerDialog view, int year, int monthOfYear,
      int dayOfMonth) {
    final long time = Converters.timeOf(year, monthOfYear, dayOfMonth);

    note.due = time;
    dueDate.setVisibility(View.VISIBLE);
    dueDate.setText(Converters.dateStringOf(time));
  }

  /**
   * получение заголовка экрана
   *
   * @return строка с заголовком
   */
  @NonNull @Override String getTitle() {
    return pageTitle;
  }

  /**
   * метод запроса прав доступа на работу с системным календарём
   */
  @AskPermission({ Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR })
  private void askAndSavePermission() {
    hasCalendarPermission = true;
  }

  /**
   * сохранение заметки в БД, если у заметки есть привязка к дате, создаётся событие в календаре
   *
   * @return флаг, успешно ли прошло сохранение
   */
  @Override boolean saveFormData() {
    final boolean shouldAddEvent = note.due != 0L && hasCalendarPermission && shouldNotify.get();

    if (note.text.isEmpty()) {
      return false;
    }

    if (shouldAddEvent) {
      addCalendarEvent();
    }

    note.save();

    return true;
  }

  /**
   * показывает всплывающее сообщение с ошибкой в случае, если у заметки нет текста(он обязателен)
   */
  @Override void showInvalidDataError() {
    Toast.makeText(getActivity(), messageNoteNoText, Toast.LENGTH_LONG).show();
  }

  /**
   * создание события в календаре
   */
  private void addCalendarEvent() {
    final String eventName = note.name.isEmpty() ? eventTitleStub : note.name;

    if (note.noteEventId != EVENT_NULL_ID) {
      Events.updateCalendarEvent(contentResolver, note.noteEventId, eventName, note.text, note.due,
          false);
    } else {
      note.noteEventId =
          Events.addEventToCalendar(contentResolver, eventName, note.text, note.due, false);
    }
  }
}
