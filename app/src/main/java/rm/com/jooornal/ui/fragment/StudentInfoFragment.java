package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import java.util.List;
import javax.inject.Inject;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.data.entity.StudentInfoEntry;
import rm.com.jooornal.ui.adapter.StudentInfoAdapter;
import rm.com.jooornal.util.Converters;

/**
 * Created by alex
 */
public final class StudentInfoFragment extends BaseContentFragment {
  private static final String KEY_STUDENT = "KEY_STUDENT";

  @Inject StudentInfoAdapter adapter;

  private Student student;

  @NonNull public static StudentInfoFragment newInstance(@NonNull Student student) {
    final Bundle args = new Bundle();
    final StudentInfoFragment fragment = new StudentInfoFragment();

    args.putParcelable(KEY_STUDENT, student);
    fragment.setArguments(args);

    return fragment;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    final List<StudentInfoEntry> infoEntries = Converters.infoEntryListOf(student);

    adapter.updateData(infoEntries);
    content.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_primary_light));
    content.setAdapter(adapter);

    toggleContent(true);
    add.hide();
  }

  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    student = args.getParcelable(KEY_STUDENT);
  }

  @Override protected void injectDependencies(@NonNull JooornalApplication app) {
    super.injectDependencies(app);
    app.injector().inject(this);
  }

  @NonNull @Override String getTitle() {
    return "";
  }

  @Override boolean hasBackButton() {
    return false;
  }

  @Override boolean isNested() {
    return true;
  }
}
