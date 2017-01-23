package rm.com.jooornal.data.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.util.List;
import java.util.concurrent.ExecutorService;
import rm.com.jooornal.data.entity.Student;

/**
 * Created by alex
 */

public final class StudentsListProvider extends ListAsyncProvider<Student> {

  public StudentsListProvider(@NonNull ExecutorService executor, @NonNull Handler mainThreadHook) {
    super(executor, mainThreadHook);
  }

  @Override protected boolean matchQuery(Student item, String query) {
    return item.name.toLowerCase().contains(query.toLowerCase()) || item.surname.toLowerCase()
        .contains(query.toLowerCase());
  }

  @Override protected List<Student> get() {
    return SQLite.select().from(Student.class).queryList();
  }
}
