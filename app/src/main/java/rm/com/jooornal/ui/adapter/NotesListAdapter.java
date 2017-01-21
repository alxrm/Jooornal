package rm.com.jooornal.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Note;
import rm.com.jooornal.ui.holder.BaseHolder;
import rm.com.jooornal.ui.holder.NoteHolder;

/**
 * Created by alex
 */

public final class NotesListAdapter extends BaseAdapter<Note, BaseHolder<Note>> {
  @Override public BaseHolder<Note> onCreateViewHolder(ViewGroup parent, int viewType) {
    return new NoteHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
  }
}
