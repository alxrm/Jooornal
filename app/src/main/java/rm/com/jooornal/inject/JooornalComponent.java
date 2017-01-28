package rm.com.jooornal.inject;

import dagger.Component;
import javax.inject.Singleton;
import rm.com.jooornal.data.services.CallReceiver;
import rm.com.jooornal.data.services.SmsReceiver;
import rm.com.jooornal.inject.modules.JooornalModule;
import rm.com.jooornal.inject.modules.NotesListModule;
import rm.com.jooornal.inject.modules.ReceiversModule;
import rm.com.jooornal.inject.modules.SettingsModule;
import rm.com.jooornal.inject.modules.StudentPageModule;
import rm.com.jooornal.inject.modules.StudentsListModule;
import rm.com.jooornal.ui.fragment.NoteFragment;
import rm.com.jooornal.ui.fragment.NotesListFragment;
import rm.com.jooornal.ui.fragment.SettingsFragment;
import rm.com.jooornal.ui.fragment.StudentCallsFragment;
import rm.com.jooornal.ui.fragment.StudentCreateFragment;
import rm.com.jooornal.ui.fragment.StudentInfoFragment;
import rm.com.jooornal.ui.fragment.StudentMessagesFragment;
import rm.com.jooornal.ui.fragment.StudentsListFragment;

@Singleton
@Component(modules = {
    JooornalModule.class,
    StudentsListModule.class,
    StudentPageModule.class,
    NotesListModule.class,
    ReceiversModule.class,
    SettingsModule.class
}) public interface JooornalComponent {
  void inject(SettingsFragment fragment);

  void inject(StudentsListFragment fragment);

  void inject(StudentMessagesFragment fragment);

  void inject(StudentCallsFragment fragment);

  void inject(StudentInfoFragment fragment);

  void inject(NotesListFragment fragment);

  void inject(SmsReceiver receiver);

  void inject(CallReceiver receiver);

  void inject(NoteFragment noteFragment);

  void inject(StudentCreateFragment studentCreateFragment);
}
