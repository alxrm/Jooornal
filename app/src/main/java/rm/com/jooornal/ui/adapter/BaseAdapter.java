package rm.com.jooornal.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import rm.com.jooornal.ui.holder.BaseHolder;

/**
 * базовый обобщённый класс, соединяющий данные и визуальную часть
 *
 * @param <M> обобщённый тип данных моделей, список которых будет
 * @param <VH> обобщённый тип данных элемента списка, содержащий в себе элементы интерфейса,
 * которые заполняются данными из модели
 */
abstract class BaseAdapter<M, VH extends BaseHolder<M>> extends RecyclerView.Adapter<VH> {

  /**
   * список с данными, которые могут обновляться
   */
  protected List<M> data = new ArrayList<>();
  protected BaseHolder.OnClickListener<M> clickListener;

  /**
   * метод, где происходит привязка данных из модели к визуальной части
   *
   * @param holder элемент интерфейса, часть списка
   * @param position позиция в списке, по которой нужно брать модель
   */
  @Override public void onBindViewHolder(VH holder, int position) {
    if (holder != null) holder.bind(data.get(position));
  }

  /**
   * метод для получения количества элементов в списке, нужен выше по иерархии
   *
   * @return возвращает число, количество элементов в списке
   */
  @Override public int getItemCount() {
    return data.size();
  }

  /**
   * метод для обновления данных и автоматической перерисовки элементов интерфейса
   *
   * @param data новые данные, которые будут записаны в переменную класса
   */
  final public void updateData(List<M> data) {
    this.data = data;
    notifyDataSetChanged();
  }

  final public void setOnClickListener(@NonNull BaseHolder.OnClickListener<M> clickListener) {
    this.clickListener = clickListener;
  }
}
