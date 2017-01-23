package rm.com.jooornal.ui.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Note;
import rm.com.jooornal.util.Converters;

/**
 * Created by alex
 */

public final class NoteHolder extends BaseHolder<Note> {

  @BindString(R.string.item_note_noname) String noName;

  @BindView(R.id.item_note_title) TextView title;
  @BindView(R.id.item_note_text) TextView text;
  @BindView(R.id.item_note_time) TextView time;
  @BindView(R.id.item_note_student) TextView student;

  public NoteHolder(View itemView) {
    super(itemView);
  }

  @Override public void bind(@NonNull final Note model) {
    final String titleText = model.name.isEmpty() ? noName : model.name;

    title.setText(titleText);
    text.setText(model.text);

    if (model.student != null) {
      student.setVisibility(View.VISIBLE);
      student.setText(Converters.shortNameOf(model.student));
    }

    if (model.due != 0L) {
      time.setVisibility(View.VISIBLE);
      time.setText(Converters.dateStringOf(model.due));
    }

    itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (clickListener != null) {
          clickListener.onItemClick(model);
        }
      }
    });
  }
}
