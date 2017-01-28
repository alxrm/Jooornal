package rm.com.jooornal.ui.fragment;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindString;
import butterknife.OnClick;
import java.util.List;
import javax.inject.Inject;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.R;
import rm.com.jooornal.data.entity.Note;
import rm.com.jooornal.data.provider.NotesListProvider;
import rm.com.jooornal.data.provider.ProviderListener;
import rm.com.jooornal.ui.adapter.NotesListAdapter;
import rm.com.jooornal.ui.holder.BaseHolder;
import rm.com.jooornal.util.Events;

public final class NotesListFragment extends BaseContentFragment
    implements BaseHolder.OnClickListener<Note>, ProviderListener<List<Note>>,
    MenuItemCompat.OnActionExpandListener, SearchView.OnQueryTextListener {

  @BindString(R.string.page_name_notes) String title;

  @Inject NotesListAdapter adapter;
  @Inject NotesListProvider provider;

  public static NotesListFragment newInstance() {
    return new NotesListFragment();
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    toggleContent(false, true);

    adapter.setOnClickListener(this);
    content.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_primary_light));
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

  @Override public void onItemClick(@NonNull Note item) {
    navigateTo(NoteFragment.newInstance(item));
  }

  @Override void onItemSwiped(int position) {
    final Note removedNote = adapter.delete(position);

    if (removedNote.noteEventId != -1) {
      final ContentResolver resolver = getActivity().getContentResolver();

      Events.deleteCalendarEvent(resolver, removedNote.noteEventId);
    }

    removedNote.delete();
  }

  @Override public void onProvide(@NonNull List<Note> payload) {
    adapter.updateData(payload);
    toggleContent(true, payload.isEmpty());
  }

  @OnClick(R.id.content_add) final void createNewNote() {
    navigateTo(NoteFragment.newInstance());
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

  private void updateQuery(String nextQuery) {
    provider.search(nextQuery, this);
  }
}
