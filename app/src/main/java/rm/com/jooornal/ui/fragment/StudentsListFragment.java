package rm.com.jooornal.ui.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import butterknife.BindString;
import butterknife.OnClick;
import com.canelmas.let.AskPermission;
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

public class StudentsListFragment extends BaseContentFragment
    implements BaseHolder.OnClickListener<Student>, ProviderListener<List<Student>> {

  @BindString(R.string.page_name_students) String title;

  @Inject StudentsListAdapter adapter;
  @Inject StudentsListProvider provider;

  @NonNull public static StudentsListFragment newInstance() {
    return new StudentsListFragment();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    askSmsAndCallsPermissions();
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    toggleContent(false, true);

    adapter.setOnClickListener(this);
    content.setAdapter(adapter);
    provider.provide(this);
  }

  @Override protected void injectDependencies(@NonNull JooornalApplication app) {
    super.injectDependencies(app);
    app.injector().inject(this);
  }

  @Override public void onProvide(@NonNull List<Student> payload) {
    adapter.updateData(payload);
    toggleContent(true, payload.isEmpty());
  }

  @Override public void onItemClick(@NonNull Student item) {
    navigateTo(StudentPageFragment.newInstance(item));
  }

  @OnClick(R.id.content_add) final void createNewStudent() {
    navigateTo(StudentCreateFragment.newInstance());
  }

  @NonNull @Override public String getTitle() {
    return title;
  }

  @Override boolean hasBackButton() {
    return false;
  }

  @Override boolean isNested() {
    return false;
  }

  @AskPermission({
      Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_PHONE_STATE
  }) private void askSmsAndCallsPermissions() {
  }
}
