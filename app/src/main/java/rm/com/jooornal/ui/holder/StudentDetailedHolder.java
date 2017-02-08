package rm.com.jooornal.ui.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Student;

/**
 * элемент списка студентов с дополнительными данными(количество смс и звонков)
 */
public final class StudentDetailedHolder extends StudentHolder {

  @BindView(R.id.item_student_indicators) RelativeLayout indicators;
  @BindView(R.id.student_call_count) TextView calls;
  @BindView(R.id.student_sms_count) TextView sms;

  public StudentDetailedHolder(View itemView) {
    super(itemView);
  }

  /**
   * привязка основных и дополнительных данных объекта студента
   *
   * @param model данные, которые должны быть привязаны и отрисованы
   */
  @Override public void bind(@NonNull Student model) {
    super.bind(model);
    indicators.setVisibility(View.VISIBLE);

    calls.setText(String.valueOf(model.getCalls().size()));
    sms.setText(String.valueOf(model.getSmsList().size()));
  }
}
