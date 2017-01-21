package rm.com.jooornal.inject;

import android.os.Handler;
import android.os.Looper;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Singleton;
import rm.com.jooornal.data.provider.StudentsListProvider;
import rm.com.jooornal.ui.adapter.StudentsListAdapter;

@Module public final class StudentsListModule {

  @Provides @Singleton static StudentsListAdapter provideStudentsListAdapter() {
    return new StudentsListAdapter();
  }

  @Provides @Singleton static Handler provideMainThreadHandler() {
    return new Handler(Looper.getMainLooper());
  }

  @Provides @Singleton static ExecutorService provideExecutorService() {
    return Executors.newSingleThreadExecutor();
  }

  @Provides @Singleton
  static StudentsListProvider provideStudentsList(ExecutorService service, Handler handler) {
    return new StudentsListProvider(service, handler);
  }
}
