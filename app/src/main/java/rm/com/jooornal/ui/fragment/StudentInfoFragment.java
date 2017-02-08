package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.util.List;
import javax.inject.Inject;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.data.entity.StudentInfoEntry;
import rm.com.jooornal.ui.adapter.StudentInfoAdapter;
import rm.com.jooornal.util.Converters;

/**
 * экран с информацией о студенте
 */
public final class StudentInfoFragment extends BaseContentFragment {
  private static final String KEY_STUDENT = "KEY_STUDENT";

  @Inject StudentInfoAdapter adapter;

  private Student student;

  /**
   * создание экрана
   *
   * @param student объект с данными студента
   * @return объект экрана
   */
  @NonNull public static StudentInfoFragment newInstance(@NonNull Student student) {
    final Bundle args = new Bundle();
    final StudentInfoFragment fragment = new StudentInfoFragment();

    args.putParcelable(KEY_STUDENT, student);
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
    final List<StudentInfoEntry> infoEntries = Converters.infoEntryListOf(student);

    adapter.updateData(infoEntries);
    content.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_primary_light));
    content.setAdapter(adapter);

    toggleContent(true, infoEntries.isEmpty());
    add.hide();
  }

  /**
   * создание меню в верхнем баре
   *
   * @param menu объект меню, к которому происходит привязка
   * @param inflater объект класса, для создания объекта меню из XML разметки
   */
  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_student_info, menu);
  }

  /**
   * выбран элемент меню
   *
   * @param item выбранный элемент
   * @return флаг, обработан ли элемент меню
   */
  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_student_info_edit) {
      navigateTo(StudentEditFragment.newInstance(student));
    }

    return super.onOptionsItemSelected(item);
  }

  /**
   * распаковка аргументов, переданных при создании экрана
   *
   * @param args сами параметры с пометкой, что они не пустые
   */
  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    student = args.getParcelable(KEY_STUDENT);
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
