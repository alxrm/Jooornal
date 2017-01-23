package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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
import rm.com.jooornal.util.Logger;

/**
 * Created by alex
 */
public final class NotesListFragment extends BaseContentFragment
    implements BaseHolder.OnClickListener<Note>, ProviderListener<List<Note>> {

  @BindString(R.string.page_name_notes) String title;

  @Inject NotesListAdapter adapter;
  @Inject NotesListProvider provider;

  public static NotesListFragment newInstance() {
    return new NotesListFragment();
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    toggleContent(false);

    adapter.setOnClickListener(this);
    content.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_primary_light));
    content.setAdapter(adapter);
    provider.provide(this);
  }

  @Override protected void injectDependencies(@NonNull JooornalApplication app) {
    super.injectDependencies(app);
    app.injector().inject(this);
  }

  @Override public void onItemClick(@NonNull Note item) {
    Logger.d(item.name);
    navigateTo(NoteFragment.newInstance(item));
  }

  @Override public void onProvide(@NonNull List<Note> payload) {
    adapter.updateData(payload);
    toggleContent(true);
  }

  @OnClick(R.id.content_add) final void createNewNote() {
    navigateTo(NoteFragment.newInstance());
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
}
