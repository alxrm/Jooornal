package rm.com.jooornal.ui.fragment;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindString;
import butterknife.OnClick;
import java.util.List;
import javax.inject.Inject;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Note;
import rm.com.jooornal.data.provider.NotesListProvider;
import rm.com.jooornal.data.provider.ProviderListener;
import rm.com.jooornal.ui.adapter.NotesListAdapter;
import rm.com.jooornal.ui.holder.BaseHolder;
import rm.com.jooornal.util.Events;

import static rm.com.jooornal.constant.Events.EVENT_NULL_ID;

/**
 * экран со списком заметок
 */
public final class NotesListFragment extends BaseContentFragment
    implements BaseHolder.OnClickListener<Note>, ProviderListener<List<Note>>,
    MenuItemCompat.OnActionExpandListener, SearchView.OnQueryTextListener {

  @BindString(R.string.page_name_notes) String title;

  @Inject NotesListAdapter adapter;
  @Inject NotesListProvider provider;
  @Inject ContentResolver contentResolver;

  /**
   * создание экрана списка заметок
   *
   * @return объект экрана
   */
  public static NotesListFragment newInstance() {
    return new NotesListFragment();
  }

  /**
   * интерфейс создан, привязка данных
   *
   * @param view корневой элемент, в котором отрисовываются элементы экрана
   * @param savedInstanceState сохранённое состояние, не используется здесь
   */
  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    toggleContent(false, true);

    adapter.setOnClickListener(this);
    content.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_primary_light));
    content.setAdapter(adapter);
    provider.provide(this);

    addSwipeBehaviour(content);
  }

  /**
   * добавление меню в верхнем баре
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
   * выбран элемент в списке
   *
   * @param item объект данных элемента
   */
  @Override public void onItemClick(@NonNull Note item) {
    navigateTo(NoteFragment.newInstance(item));
  }

  /**
   * произведён свайп элемента в списке
   *
   * @param position позиция элемента в списке
   */
  @Override void onItemSwiped(int position) {
    final Note removedNote = adapter.delete(position);

    if (removedNote.noteEventId != EVENT_NULL_ID) {
      Events.deleteCalendarEvent(contentResolver, removedNote.noteEventId);
    }

    provider.delete(removedNote);
    removedNote.delete();
  }

  /**
   * возвращение списка заметок из базы данных
   *
   * @param payload сам результат, экземпляр типа данных результата
   */
  @Override public void onProviderResult(@NonNull List<Note> payload) {
    adapter.updateData(payload);
    toggleContent(true, payload.isEmpty());
  }

  /**
   * создать новую заметку, переход в экран заметки
   */
  @OnClick(R.id.content_add) final void createNewNote() {
    navigateTo(NoteFragment.newInstance());
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
   * передача поисковой строки в провайдер для обновления списка
   *
   * @param nextQuery строка, текущий поисковой запрос
   */
  private void updateQuery(String nextQuery) {
    provider.search(nextQuery, this);
  }
}
