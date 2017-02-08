package rm.com.jooornal.ui.holder;

import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.util.Converters;

/**
 * элемент списка студентов
 */
public class StudentHolder extends BaseHolder<Student> {

  @BindView(R.id.item_student_name) TextView name;
  @BindView(R.id.item_student_phone) TextView phone;
  @BindView(R.id.item_student_icon) TextView icon;

  public StudentHolder(View itemView) {
    super(itemView);
  }

  /**
   * привязка данных студента
   *
   * @param model данные, которые должны быть привязаны и отрисованы
   */
  @Override public void bind(@NonNull final Student model) {
    final String shortName = Converters.shortNameOf(model);
    final String letters = Converters.iconLettersOf(model.surname, model.name);
    final String phoneNumber = Converters.formatPhoneNumberOf(model.getPhones().get(0).phoneNumber);
    final ColorFilter colorFilter =
        new PorterDuffColorFilter(Converters.colorOf(model.surname), PorterDuff.Mode.MULTIPLY);

    name.setText(shortName);
    phone.setText(phoneNumber);
    icon.setText(letters);
    icon.getBackground().setColorFilter(colorFilter);

    itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (clickListener != null) {
          clickListener.onItemClick(model);
        }
      }
    });
  }
}
