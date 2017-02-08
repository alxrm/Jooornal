package rm.com.jooornal.ui.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.StudentInfoEntry;

/**
 * элемент списка, отображающего описание студента
 * элемент с названием какой-либо части описания
 */
public final class TitleEntryHolder extends BaseHolder<StudentInfoEntry> {

  @BindView(R.id.item_entry_title) TextView title;

  public TitleEntryHolder(View itemView) {
    super(itemView);
  }

  /**
   * привязка заголовка
   *
   * @param model данные, которые должны быть привязаны и отрисованы
   */
  @Override public void bind(@NonNull StudentInfoEntry model) {
    title.setText(model.text);
  }
}
