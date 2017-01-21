package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import butterknife.BindString;
import butterknife.OnClick;
import java.util.List;
import javax.inject.Inject;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Student;
import rm.com.jooornal.data.provider.ProviderListener;
import rm.com.jooornal.data.provider.StudentsListProvider;
import rm.com.jooornal.ui.adapter.StudentsListAdapter;
import rm.com.jooornal.ui.holder.BaseHolder;

/**
 * Created by alex
 */

public final class StudentsListFragment extends BaseContentFragment
    implements BaseHolder.OnClickListener<Student>, ProviderListener<List<Student>> {

  @BindString(R.string.page_name_students) String title;

  @Inject StudentsListAdapter adapter;
  @Inject StudentsListProvider provider;

  public static StudentsListFragment newInstance() {
    return new StudentsListFragment();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final JooornalApplication app = getApplication();

    if (app != null) {
      app.injector().inject(this);
    }
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    toggleContent(false);

    adapter.setOnClickListener(this);
    content.setAdapter(adapter);
    provider.provide(this);
  }

  @Override public void onProvide(@NonNull List<Student> payload) {
    adapter.updateData(payload);
    toggleContent(true);
  }

  @Override public void onItemClick(@NonNull Student item) {
    navigateTo(StudentFragment.newInstance(item));
  }

  @OnClick(R.id.content_add) void createNewStudent() {
    navigateTo(StudentCreateFragment.newInstance());
  }

  @NonNull @Override public String getTitle() {
    return title;
  }

  @Override boolean hasBackButton() {
    return false;
  }
}
