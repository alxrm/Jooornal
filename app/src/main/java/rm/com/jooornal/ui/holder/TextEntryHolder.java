package rm.com.jooornal.ui.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.StudentInfoEntry;

public final class TextEntryHolder extends BaseHolder<StudentInfoEntry> {

  @BindView(R.id.item_entry_text) TextView text;

  public TextEntryHolder(View itemView) {
    super(itemView);
  }

  @Override public void bind(@NonNull StudentInfoEntry model) {
    text.setText(model.text);
  }
}
