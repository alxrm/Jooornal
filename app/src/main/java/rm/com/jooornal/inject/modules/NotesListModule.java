package rm.com.jooornal.inject.modules;

import android.os.Handler;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.ExecutorService;
import javax.inject.Singleton;
import rm.com.jooornal.data.provider.NotesListProvider;
import rm.com.jooornal.ui.adapter.NotesListAdapter;

@Module public final class NotesListModule {

  @Provides @Singleton static NotesListAdapter provideNotesListAdapter() {
    return new NotesListAdapter();
  }

  @Provides @Singleton
  static NotesListProvider provideNotesList(ExecutorService service, Handler handler) {
    return new NotesListProvider(service, handler);
  }
}
