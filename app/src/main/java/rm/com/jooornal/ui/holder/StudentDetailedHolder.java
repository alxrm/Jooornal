package rm.com.jooornal.ui.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Student;

/**
 * Created by alex
 */

public final class StudentDetailedHolder extends StudentHolder {

  @BindView(R.id.item_student_indicators) RelativeLayout indicators;
  @BindView(R.id.student_call_count) TextView calls;
  @BindView(R.id.student_sms_count) TextView sms;
  @BindView(R.id.student_notes_count) TextView notes;

  public StudentDetailedHolder(View itemView) {
    super(itemView);
  }

  @Override public void bind(@NonNull Student model) {
    super.bind(model);
    indicators.setVisibility(View.VISIBLE);

    calls.setText(String.valueOf(model.getCalls().size()));
    sms.setText(String.valueOf(model.getSmsList().size()));
    notes.setText(String.valueOf(model.getNotes().size()));
  }
}
