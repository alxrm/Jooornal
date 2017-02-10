package rm.com.jooornal.ui.fragment;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import rm.com.jooornal.R;

/**
 * абстрактный класс экрана, в котором есть форма добавления данных в бд
 */
public abstract class BaseFormFragment extends BaseFragment {

  /**
   * создание меню в верхнем баре
   *
   * @param menu объект меню, к которому происходит привязка
   * @param inflater объект класса, для создания объекта меню из XML разметки
   */
  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_create_new, menu);
  }

  /**
   * выбран элемент меню
   *
   * @param item выбранный элемент
   * @return флаг, обработан ли элемент меню
   */
  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_create_done) {
      final boolean hasSavedSuccessfully = saveFormData();

      if (hasSavedSuccessfully) {
        navigateUp();
      } else {
        showInvalidDataError();
      }
    }

    return super.onOptionsItemSelected(item);
  }

  /**
   * возвращает флаг о наличии кнопки назад
   *
   * @return флаг наличия
   */
  @Override boolean hasBackButton() {
    return true;
  }

  /**
   * возвращает флаг, является ли экран вложенным
   *
   * @return флаг вложенности
   */
  @Override boolean isNested() {
    return false;
  }

  /**
   * показывает всплывающее сообщение в случае ошибочных данных или их нехватки, при заполнении
   * формы
   */
  abstract void showInvalidDataError();

  /**
   * сохраняет данные из формы, возвращает флаг, успешно ли прошло сохранение
   *
   * @return флаг, подтверждающий, что сохранение прошло успешно
   */
  abstract boolean saveFormData();
}
