package rm.com.jooornal.data.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.util.List;
import java.util.concurrent.ExecutorService;
import rm.com.jooornal.data.entity.Note;
import rm.com.jooornal.data.entity.Note_Table;

/**
 * провайдер удаления старых заметок
 */
public final class DeleteOldNotesProvider extends ParametrisedAsyncProvider<Long, Integer> {

  public DeleteOldNotesProvider(@NonNull ExecutorService executor,
      @NonNull Handler mainThreadHook) {
    super(executor, mainThreadHook);
  }

  /**
   * удаление заметок из бд
   *
   * @return количество удалённых заметок
   */
  @Override protected Integer execute() {
    if (currentParam == null) {
      return 0;
    }

    final List<Note> oldNotes =
        SQLite.select().from(Note.class).where(Note_Table.due.lessThan(currentParam)).queryList();

    SQLite.delete(Note.class).where(Note_Table.due.lessThan(currentParam)).execute();

    return oldNotes.size();
  }
}
