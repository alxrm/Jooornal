package rm.com.jooornal.data.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.util.List;
import java.util.concurrent.ExecutorService;
import rm.com.jooornal.data.entity.Note;
import rm.com.jooornal.data.entity.Note_Table;
import rm.com.jooornal.util.Lists;

/**
 * провайдер списка заметок
 */
public final class NotesListProvider extends ListAsyncProvider<Note> {
  public NotesListProvider(@NonNull ExecutorService executor, @NonNull Handler mainThreadHook) {
    super(executor, mainThreadHook);
  }

  /**
   * проверка, совпадает ли заметка с текстом запроса
   *
   * @param item текущий проверяемый элемент
   * @param query строка с запросом
   * @return флаг, пройдена ли проверка
   */
  @Override protected boolean matchQuery(Note item, String query) {
    return item.name.toLowerCase().contains(query.toLowerCase()) || item.text.toLowerCase()
        .contains(query.toLowerCase());
  }

  /**
   * получение результата запроса к БД
   *
   * @return все заметки
   */
  @Override protected List<Note> execute() {
    final List<Note> notes =
        SQLite.select().from(Note.class).orderBy(Note_Table.time, false).queryList();

    return Lists.map(notes, new Lists.Transformer<Note, Note>() {
      @Override public Note invoke(Note item) {
        if (item.student != null) {
          item.student.load();
        }

        return item;
      }
    });
  }
}
