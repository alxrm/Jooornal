package rm.com.jooornal.data.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.util.List;
import java.util.concurrent.ExecutorService;
import rm.com.jooornal.data.entity.Student;

/**
 * провайдер списка студентов
 */
public final class StudentsListProvider extends ListAsyncProvider<Student> {

  public StudentsListProvider(@NonNull ExecutorService executor, @NonNull Handler mainThreadHook) {
    super(executor, mainThreadHook);
  }

  /**
   * проверка, совпадает ли объект студента с текстом запроса
   *
   * @param item текущий проверяемый элемент
   * @param query строка с запросом
   * @return флаг, пройдена ли проверка
   */
  @Override protected boolean matchQuery(Student item, String query) {
    return item.name.toLowerCase().contains(query.toLowerCase()) || item.surname.toLowerCase()
        .contains(query.toLowerCase());
  }

  /**
   * получение всех студентов из базы данных
   *
   * @return список студентов
   */
  @Override protected List<Student> execute() {
    return SQLite.select().from(Student.class).queryList();
  }
}
