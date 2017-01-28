package rm.com.jooornal.ui.holder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;

/**
 * базовый обобщённый класс описывающий элемент списка
 *
 * @param <T> тип модели, данными из которой будет заполнен этот элемент списка
 */
@SuppressWarnings("ALL") public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {

  protected OnClickListener<T> clickListener;

  public BaseHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  /**
   * метод привязки данных к визуальной части
   *
   * @param model данные, которые должны быть привязаны и отрисованы
   */
  abstract public void bind(@NonNull T model);

  /**
   * метод для сохранения слушателя нажатия, позже слушатель может быть подключён к элементу, если
   * это потребуется
   *
   * @param clickListener экземпляр класса, реализующего слушателя нажатия
   */
  public final void setOnClickListener(@Nullable OnClickListener<T> clickListener) {
    this.clickListener = clickListener;
  }

  /**
   * слушатель нажатия на элемент списка
   *
   * @param <T> модель, которая хранилась в нажатом элементе
   */
  public interface OnClickListener<T> {
    void onItemClick(@NonNull T item);
  }
}