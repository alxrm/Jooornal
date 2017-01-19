package rm.com.jooornal.data.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.util.List;
import java.util.concurrent.ExecutorService;
import rm.com.jooornal.data.entity.Note;

/**
 * Created by alex
 */

public final class NotesListProvider extends ListAsyncProvider<Note> {
  public NotesListProvider(@NonNull ExecutorService executor, @NonNull Handler mainThreadHook) {
    super(executor, mainThreadHook);
  }

  @Override protected boolean matchQuery(Note item, String query) {
    return item.name.toLowerCase().contains(query.toLowerCase()) || item.text.toLowerCase()
        .contains(query.toLowerCase());
  }

  @Override protected List<Note> get() {
    return SQLite.select().from(Note.class).queryList();
  }
}
