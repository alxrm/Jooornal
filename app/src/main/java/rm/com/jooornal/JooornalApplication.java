package rm.com.jooornal;

import android.app.Application;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import rm.com.jooornal.inject.DaggerJooornalComponent;
import rm.com.jooornal.inject.JooornalComponent;
import rm.com.jooornal.inject.modules.JooornalModule;

/**
 * класс приложения, здесь происходит инициализация
 */
public final class JooornalApplication extends Application {

  private JooornalComponent component;

  @Override public void onCreate() {
    super.onCreate();
    final FlowConfig databaseConfig =
        new FlowConfig.Builder(this).openDatabasesOnInit(true).build();

    FlowManager.init(databaseConfig);

    component = DaggerJooornalComponent.builder()
        .jooornalModule(new JooornalModule(this))
        .build();
  }

  /**
   * @return возвращает контейнер зависимостей
   */
  final public JooornalComponent injector() {
    return component;
  }
}
