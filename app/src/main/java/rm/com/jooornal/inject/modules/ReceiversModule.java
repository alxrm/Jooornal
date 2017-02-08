package rm.com.jooornal.inject.modules;

import android.os.Handler;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.ExecutorService;
import javax.inject.Singleton;
import rm.com.jooornal.data.provider.PhoneProvider;

/**
 * класс инициализации зависимостей для фоновых сервисов
 */
@Module public final class ReceiversModule {

  @Provides @Singleton static PhoneProvider providePhone(ExecutorService service, Handler handler) {
    return new PhoneProvider(service, handler);
  }
}
