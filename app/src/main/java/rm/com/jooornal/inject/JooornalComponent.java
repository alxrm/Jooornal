package rm.com.jooornal.inject;

import dagger.Component;
import javax.inject.Singleton;
import rm.com.jooornal.ui.fragment.StudentsListFragment;

/**
 * Created by alex
 */

@Singleton @Component(modules = StudentsListModule.class)
public interface JooornalComponent {
  void inject(StudentsListFragment fragment);
}
