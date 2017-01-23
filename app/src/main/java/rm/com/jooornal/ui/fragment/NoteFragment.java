package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.Calendar;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Note;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.ui.OnActionListener;
import rm.com.jooornal.util.Converters;

/**
 * Created by alex
 */
public final class NoteFragment extends BaseFragment
    implements StudentPickerFragment.OnStudentPickerListener, DatePickerDialog.OnDateSetListener {

  private static final String KEY_NOTE = "KEY_NOTE";

  @BindString(R.string.page_name_note_page) String pageTitle;

  @BindView(R.id.note_create_student) TextView student;
  @BindView(R.id.note_create_time) TextView dueDate;
  @BindView(R.id.note_create_text) EditText text;
  @BindView(R.id.note_create_title) EditText title;

  private Note note = new Note();

  @NonNull public static NoteFragment newInstance() {
    return new NoteFragment();
  }

  @NonNull public static NoteFragment newInstance(@NonNull Note note) {
    final Bundle args = new Bundle();
    final NoteFragment fragment = new NoteFragment();

    args.putParcelable(KEY_NOTE, note);
    fragment.setArguments(args);

    return fragment;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_note_page, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    title.setText(note.name);
    text.setText(note.text);

    if (note.due != 0L) {
      dueDate.setVisibility(View.VISIBLE);
      dueDate.setText(Converters.dateStringOf(note.due));
    }

    if (note.student != null) {
      student.setVisibility(View.VISIBLE);
      student.setText(Converters.shortNameOf(note.student));
    }
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_create_new, menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_create_done) {
      addNote();
    }

    return super.onOptionsItemSelected(item);
  }

  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    note = args.getParcelable(KEY_NOTE);
  }

  @OnTextChanged(R.id.note_create_text) final void onNoteTextChanged(CharSequence text) {
    note.text = text.toString();
  }

  @OnTextChanged(R.id.note_create_title) final void onNoteTitleChanged(CharSequence title) {
    note.name = title.toString();
  }

  @OnClick(R.id.note_create_student) final void onDeleteStudent() {
    ask("Отменить привязку к студенту?", new OnActionListener() {
      @Override public void onAction() {
        note.student = null;
        student.setVisibility(View.GONE);
      }
    });
  }

  @OnClick(R.id.note_create_time) final void onDeleteDate() {
    ask("Сбросить дату уведомления?", new OnActionListener() {
      @Override public void onAction() {
        note.due = 0L;
        dueDate.setVisibility(View.GONE);
      }
    });
  }

  @OnClick(R.id.note_create_assign_student) final void onAssignStudent() {
    final StudentPickerFragment pickerFragment = StudentPickerFragment.newInstance();
    pickerFragment.setPickerListener(this);

    navigateTo(pickerFragment);
  }

  @Override public void onStudentPicked(@NonNull Student picked) {
    note.student = picked;
    student.setVisibility(View.VISIBLE);
    student.setText(Converters.shortNameOf(picked));
  }

  @OnClick(R.id.note_create_assign_time) final void onAssignDueDate() {
    final Calendar birth = Calendar.getInstance();
    final DatePickerDialog dpd =
        DatePickerDialog.newInstance(this, birth.get(Calendar.YEAR), birth.get(Calendar.MONTH),
            birth.get(Calendar.DAY_OF_MONTH));

    dpd.show(getFragmentManager(), "dpd");
  }

  @Override
  public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
    final long time = Converters.timeOf(year, monthOfYear, dayOfMonth);

    note.due = time;
    dueDate.setVisibility(View.VISIBLE);
    dueDate.setText(Converters.dateStringOf(time));
  }

  @NonNull @Override String getTitle() {
    return pageTitle;
  }

  @Override boolean hasBackButton() {
    return true;
  }

  @Override boolean isNested() {
    return false;
  }

  private void addNote() {
    if (note.text.isEmpty()) {
      Toast.makeText(getActivity(), "У заметки должен быть текст", Toast.LENGTH_LONG).show();
      return;
    }

    note.save();
    navigateUp();
  }
}
