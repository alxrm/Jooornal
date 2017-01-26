package rm.com.jooornal.inject;

import dagger.Component;
import javax.inject.Singleton;
import rm.com.jooornal.data.services.CallReceiver;
import rm.com.jooornal.data.services.SmsReceiver;
import rm.com.jooornal.ui.fragment.*;

/**
 * Created by alex
 */

@Singleton @Component(modules = {
    StudentsListModule.class,
    StudentPageModule.class,
    NotesListModule.class,
    ReceiversModule.class
})
public interface JooornalComponent {
  void inject(StudentsListFragment fragment);
  void inject(StudentMessagesFragment fragment);
  void inject(StudentCallsFragment fragment);
  void inject(StudentInfoFragment fragment);
  void inject(NotesListFragment fragment);
  void inject(SmsReceiver receiver);
  void inject(CallReceiver receiver);
}
