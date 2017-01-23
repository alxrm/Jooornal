package rm.com.jooornal.inject;

import android.os.Handler;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.ExecutorService;
import javax.inject.Singleton;
import rm.com.jooornal.data.provider.NotesListProvider;
import rm.com.jooornal.ui.adapter.NotesListAdapter;

/**
 * Created by alex
 */
@Module public final class NotesListModule {

  @Provides @Singleton static NotesListAdapter provideNotesListAdapter() {
    return new NotesListAdapter();
  }

  @Provides @Singleton
  static NotesListProvider provideNotesList(ExecutorService service, Handler handler) {
    return new NotesListProvider(service, handler);
  }
}
