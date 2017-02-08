package rm.com.jooornal.inject.modules;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Singleton;
import rm.com.jooornal.JooornalApplication;

import static android.content.Context.MODE_PRIVATE;

/**
 * класс инициализации зависимостей на уровне приложения
 */
@Module public final class JooornalModule {
  private static final String PREFERENCES_NAME = "jooornal";

  private final JooornalApplication app;

  public JooornalModule(JooornalApplication application) {
    app = application;
  }

  @Provides @Singleton static Handler provideMainThreadHandler() {
    return new Handler(Looper.getMainLooper());
  }

  @Provides @Singleton static ExecutorService provideExecutorService() {
    return Executors.newSingleThreadExecutor();
  }

  @Provides @Singleton ContentResolver provideContentResolver() {
    return app.getContentResolver();
  }

  @Provides @Singleton SharedPreferences provideSharedPreferences() {
    return app.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
  }
}
