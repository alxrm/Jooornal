package rm.com.jooornal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import rm.com.jooornal.R;

import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;

public abstract class BaseContentFragment extends BaseFragment {

  @BindView(R.id.content_loader) ProgressBar loader;
  @BindView(R.id.content_message_empty) TextView empty;
  @BindView(R.id.content_list) RecyclerView content;
  @BindView(R.id.content_add) FloatingActionButton add;

  /**
   * метод, вызываемый при создании интерфейса экрана
   *
   * @param inflater системный класс для преобразования XML вёрстки в объект интерфейса в коде
   * @param container родительский элемент, в котором отрисовывается окно
   * @param savedInstanceState сохранённое состояние, не используется здесь
   * @return только что созданный объект с интерфейсом экрана
   */
  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_content, container, false);
  }

  void onItemSwiped(int position) {
  }

  /**
   * метод для показа/скрытия индикатора загрузки/контента
   *
   * @param show флаг, определяющий, показывается или скрывается контент
   */
  final protected void toggleContent(boolean show, boolean isEmpty) {
    content.setVisibility(show ? View.VISIBLE : View.GONE);
    loader.setVisibility(show ? View.GONE : View.VISIBLE);
    empty.setVisibility(isEmpty && show ? View.VISIBLE : View.GONE);

    if (show) {
      add.show();
    } else {
      add.hide();
    }
  }

  final protected void addSwipeBehaviour(@NonNull RecyclerView listView) {
    final Callback callback = new SimpleCallback(0, LEFT | RIGHT) {
      @Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
          RecyclerView.ViewHolder target) {
        return false;
      }

      @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        onItemSwiped(viewHolder.getAdapterPosition());
      }
    };

    final ItemTouchHelper touchHelper = new ItemTouchHelper(callback);

    touchHelper.attachToRecyclerView(listView);
  }
}
