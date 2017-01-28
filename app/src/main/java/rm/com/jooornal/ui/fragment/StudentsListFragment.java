package rm.com.jooornal.ui.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import rm.com.jooornal.util.Events;

public class StudentsListFragment extends BaseContentFragment
    implements BaseHolder.OnClickListener<Student>, ProviderListener<List<Student>>,
    MenuItemCompat.OnActionExpandListener, SearchView.OnQueryTextListener {

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

    addSwipeBehaviour(content);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_search, menu);
    super.onCreateOptionsMenu(menu, inflater);

    final MenuItem searchItem = menu.findItem(R.id.action_search);
    final SearchView searchView = (SearchView) searchItem.getActionView();

    MenuItemCompat.setOnActionExpandListener(searchItem, this);
    searchView.setOnQueryTextListener(this);
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

  @Override void onItemSwiped(int position) {
    final Student removedStudent = adapter.delete(position);

    if (removedStudent.birthDayEventId != -1) {
      final ContentResolver resolver = getActivity().getContentResolver();

      Events.deleteCalendarEvent(resolver, removedStudent.birthDayEventId);
    }

    removedStudent.delete();
  }

  @OnClick(R.id.content_add) final void createNewStudent() {
    navigateTo(StudentCreateFragment.newInstance());
  }

  @Override public boolean onMenuItemActionExpand(MenuItem item) {
    return true;
  }

  @Override public boolean onMenuItemActionCollapse(MenuItem item) {
    updateQuery("");
    return true;
  }

  @Override public boolean onQueryTextSubmit(String query) {
    return false;
  }

  @Override public boolean onQueryTextChange(String newText) {
    updateQuery(newText);
    return true;
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

  private void updateQuery(String nextQuery) {
    provider.search(nextQuery, this);
  }
}
