package rm.com.jooornal.inject.modules;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import rm.com.jooornal.ui.adapter.CallsListAdapter;
import rm.com.jooornal.ui.adapter.SmsListAdapter;
import rm.com.jooornal.ui.adapter.StudentInfoAdapter;

/**
 * класс инициализации зависимостей для экрана страницы студента
 */
@Module public final class StudentPageModule {

  @Provides @Singleton static CallsListAdapter provideCallsListAdapter() {
    return new CallsListAdapter();
  }

  @Provides @Singleton static SmsListAdapter provideMessagesListAdapter() {
    return new SmsListAdapter();
  }

  @Provides @Singleton static StudentInfoAdapter provideStudentInfoAdapter() {
    return new StudentInfoAdapter();
  }
}
