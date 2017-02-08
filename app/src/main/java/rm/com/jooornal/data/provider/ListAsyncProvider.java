package rm.com.jooornal.data.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import java.util.List;
import java.util.concurrent.ExecutorService;
import rm.com.jooornal.util.Lists;

/**
 * абстрактный класс асинхронного провайдера, возвращающего в качестве результата список какого-то
 * одного типа
 *
 * @param <T> тип элемента результирующего списка
 */
@SuppressWarnings("WeakerAccess") public abstract class ListAsyncProvider<T>
    extends AbstractAsyncProvider<List<T>> {

  public ListAsyncProvider(@NonNull ExecutorService executor, @NonNull Handler mainThreadHook) {
    super(executor, mainThreadHook);
  }

  /**
   * поиск по закэшированному списку, если он есть
   *
   * @param clause строка с текстом запроса
   * @param callback объект слушателя результата
   */
  public void search(@NonNull final String clause,
      @NonNull final ProviderListener<List<T>> callback) {
    if (cachedResult == null) {
      return;
    }

    if (clause.isEmpty()) {
      postCallback(cachedResult, callback);
      return;
    }

    executor.submit(new Runnable() {
      @Override public void run() {
        postCallback(searchByQuery(clause), callback);
      }
    });
  }

  /**
   * удаление элемента из закэшированного списка
   *
   * @param element элемент, который нужно удалить
   */
  public void delete(@NonNull T element) {
    if (cachedResult == null) {
      return;
    }

    cachedResult.remove(element);
  }

  /**
   * проверка на соответствие текущего элемента списка со строкой поискового запроса
   *
   * @param item текущий проверяемый элемент
   * @param query строка с запросом
   * @return флаг, пройдена ли проверка
   */
  protected abstract boolean matchQuery(T item, String query);

  /**
   * создание списка с результатами поискового запроса, происходит фильтрация всех элементов, не прошедших проверку
   *
   * @param clause строка с поисковым запросом, по которой осуществляется проверка
   * @return список с результатами поиска
   */
  private List<T> searchByQuery(final String clause) {
    return Lists.filter(cachedResult, new Lists.Predicate<T>() {
      @Override public boolean test(T item) {
        return matchQuery(item, clause);
      }
    });
  }
}
