package rm.com.jooornal.ui.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindString;
import butterknife.OnClick;
import com.canelmas.let.AskPermission;
import java.util.List;
import javax.inject.Inject;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.data.provider.ProviderListener;
import rm.com.jooornal.data.provider.StudentsListProvider;
import rm.com.jooornal.ui.adapter.StudentsListAdapter;
import rm.com.jooornal.ui.holder.BaseHolder;
import rm.com.jooornal.util.Events;

/**
 * экран списка студентов
 */
public class StudentsListFragment extends BaseContentFragment
    implements BaseHolder.OnClickListener<Student>, ProviderListener<List<Student>>,
    MenuItemCompat.OnActionExpandListener, SearchView.OnQueryTextListener {

  @BindString(R.string.page_name_students) String title;

  @Inject StudentsListAdapter adapter;
  @Inject StudentsListProvider provider;
  @Inject ContentResolver contentResolver;

  /**
   * создание объекта экрана списка студентов
   *
   * @return объект экрана
   */
  @NonNull public static StudentsListFragment newInstance() {
    return new StudentsListFragment();
  }

  /**
   * создание экрана, в нём происходит запрос на получение прав доступа
   *
   * @param savedInstanceState сохранённое состояние, не используется здесь
   */
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    askSmsAndCallsPermissions();
  }

  /**
   * интерфейс экрана создан, привязка к данным
   *
   * @param view корневой элемент, в котором отрисовываются элементы экрана
   * @param savedInstanceState сохранённое состояние, не используется здесь
   */
  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    toggleContent(false, true);

    adapter.setOnClickListener(this);
    content.setAdapter(adapter);
    provider.provide(this);

    addSwipeBehaviour(content);
  }

  /**
   * создание меню в верхнем баре
   *
   * @param menu объект меню, к которому происходит привязка
   * @param inflater объект класса, для создания объекта меню из XML разметки
   */
  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_search, menu);
    super.onCreateOptionsMenu(menu, inflater);

    final MenuItem searchItem = menu.findItem(R.id.action_search);
    final SearchView searchView = (SearchView) searchItem.getActionView();

    MenuItemCompat.setOnActionExpandListener(searchItem, this);
    searchView.setOnQueryTextListener(this);
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
   * результат провайдера
   *
   * @param payload сам результат, список студентов
   */
  @Override public void onProviderResult(@NonNull List<Student> payload) {
    adapter.updateData(payload);
    toggleContent(true, payload.isEmpty());
  }

  /**
   * выбран студент в списке, переход к экрану с информацией о студенте
   *
   * @param item объект с данными студента
   */
  @Override public void onItemClick(@NonNull Student item) {
    navigateTo(StudentPageFragment.newInstance(item));
  }

  /**
   * произведён свайп по элементу списка студентов(удаление студента из БД)
   *
   * @param position позиция элемента в списке
   */
  @Override void onItemSwiped(int position) {
    final Student removedStudent = adapter.delete(position);

    if (removedStudent.birthDayEventId != -1) {
      Events.deleteCalendarEvent(contentResolver, removedStudent.birthDayEventId);
    }

    provider.delete(removedStudent);
    removedStudent.delete();
  }

  /**
   * нажата кнопка добавления нового студента, переход к экрану добавления студента
   */
  @OnClick(R.id.content_add) final void createNewStudent() {
    navigateTo(StudentCreateFragment.newInstance());
  }

  /**
   * открывать ли строку поиска при нажатии на иконку в верхнем баре
   *
   * @param item нажатый элемент
   * @return флаг открытия строки поиска
   */
  @Override public boolean onMenuItemActionExpand(MenuItem item) {
    return true;
  }

  /**
   * скрывать ли строку поиска и что делать при скрытии
   *
   * @param item элемент, который вызвал её появление
   * @return флаг, стоит ли скрывать
   */
  @Override public boolean onMenuItemActionCollapse(MenuItem item) {
    updateQuery("");
    return true;
  }

  /**
   * нажат Enter при вводе поискового запроса
   *
   * @param query строка с запросом
   * @return флаг, стоит ли реагировать на нажатие
   */
  @Override public boolean onQueryTextSubmit(String query) {
    return false;
  }

  /**
   * изменён текст в поисковой строке
   *
   * @param newText новый текст запроса
   * @return стоит ли менять текст в поисковой строке
   */
  @Override public boolean onQueryTextChange(String newText) {
    updateQuery(newText);
    return true;
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
   * имеет ли экран кнопку возврата назад
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
    return false;
  }

  /**
   * запрос разрешения на права доступа для отслеживания входящих СМС и звонков(от студентов)
   */
  @AskPermission({
      Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_PHONE_STATE
  }) private void askSmsAndCallsPermissions() {
  }

  /**
   * передача поисковой строки в провайдер для обновления списка
   *
   * @param nextQuery строка, текущий поисковой запрос
   */
  private void updateQuery(String nextQuery) {
    provider.search(nextQuery, this);
  }
}
