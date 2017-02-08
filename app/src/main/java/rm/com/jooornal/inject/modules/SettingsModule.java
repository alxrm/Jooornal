package rm.com.jooornal.inject.modules;

import android.content.SharedPreferences;
import android.os.Handler;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.ExecutorService;
import javax.inject.Singleton;
import rm.com.jooornal.data.preferences.BooleanPreference;
import rm.com.jooornal.data.provider.DeleteOldNotesProvider;
import rm.com.jooornal.inject.qualifiers.BirthdayNotifications;
import rm.com.jooornal.inject.qualifiers.NoteNotifications;

/**
 * класс инициализации зависимостей для экрана настроек
 */
@Module public final class SettingsModule {
  private static final boolean DEFAULT_NOTE_NOTIFICATIONS = true;
  private static final boolean DEFAULT_BIRTHDAY_NOTIFICATIONS = true;

  @Provides @Singleton
  static DeleteOldNotesProvider provideOldNotes(ExecutorService service, Handler handler) {
    return new DeleteOldNotesProvider(service, handler);
  }

  @Provides @Singleton @NoteNotifications
  static BooleanPreference provideNoteNotificationsPreference(SharedPreferences prefs) {
    return new BooleanPreference(prefs, "note-notifications", DEFAULT_NOTE_NOTIFICATIONS);
  }

  @Provides @NoteNotifications
  static Boolean provideNoteNotifications(@NoteNotifications BooleanPreference pref) {
    return pref.get();
  }

  @Provides @Singleton @BirthdayNotifications
  static BooleanPreference provideBirthdayNotificationsPreference(SharedPreferences prefs) {
    return new BooleanPreference(prefs, "birthday-notifications", DEFAULT_BIRTHDAY_NOTIFICATIONS);
  }

  @Provides @BirthdayNotifications
  static Boolean provideBirthdayNotifications(@BirthdayNotifications BooleanPreference pref) {
    return pref.get();
  }
}
