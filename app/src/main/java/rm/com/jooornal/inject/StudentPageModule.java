package rm.com.jooornal.inject;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import rm.com.jooornal.ui.adapter.CallsListAdapter;
import rm.com.jooornal.ui.adapter.SmsListAdapter;

/**
 * Created by alex
 */

@Module public final class StudentPageModule {

  @Provides @Singleton static CallsListAdapter provideCallsListAdapter() {
    return new CallsListAdapter();
  }

  @Provides @Singleton static SmsListAdapter provideMessagesListAdapter() {
    return new SmsListAdapter();
  }
}
