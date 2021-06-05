package nguyentiendung.example.todo_navigation.ui.favourite;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import nguyentiendung.example.todo_navigation.CreateActivity;
import nguyentiendung.example.todo_navigation.Database;
import nguyentiendung.example.todo_navigation.MainActivity;
import nguyentiendung.example.todo_navigation.R;
import nguyentiendung.example.todo_navigation.Todo;
import nguyentiendung.example.todo_navigation.TodoAdapter;
import nguyentiendung.example.todo_navigation.UpdateActivity;
import nguyentiendung.example.todo_navigation.ui.home.HomeFragment;
import nguyentiendung.example.todo_navigation.ui.home.HomeViewModel;

import static android.app.Activity.RESULT_OK;

public class FavouriteFragment extends ListFragment {
    public final static String EXTRA_MAIN_TITLE = ".project2.example.EXTRA_MAIN_TITLE";
    public final static String EXTRA_MAIN_CONTENT = ".project2.example.EXTRA_MAIN_CONTENT";
    final static int TEXT_REQUEST_CREATE = 2;
    final static int TEXT_REQUEST_UPDATE = 3;
    int index = -1;
    ArrayList<Todo> arrayTodos = new ArrayList<>();
    private HomeViewModel homeViewModel;
    Database database;
    FloatingActionButton fabNew;
    TodoAdapter todoAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        fabNew = (FloatingActionButton) root.findViewById(R.id.fab_new);
        todoAdapter = new TodoAdapter(getContext(), R.layout.todo_line, arrayTodos, null, null, FavouriteFragment.this, null);
        setListAdapter(todoAdapter);
        getDatabase();
        setHasOptionsMenu(true);
        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateActivity();
            }
        });
        return root;
    }

    public void getDatabase() {
        arrayTodos.clear();
        database = ((MainActivity)getActivity()).getDatabase();
        String getDatabase = "SELECT todo.id, title, content, finish, favourite, time, location, topic.topicname, topic.id FROM todo, topic WHERE favourite = 1 AND todo.topic = topic.id";
        queryTodos(getDatabase);
    }

    public void showMenuLine(View view, int id, int position) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_line, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        DialogDeleteTodo(arrayTodos.get(position).getTitle(), id, position);
                        break;
                    case R.id.update:
                        index = arrayTodos.get(position).getId();
                        Intent updateIntent = new Intent(getContext(), UpdateActivity.class);
                        String title = arrayTodos.get(position).getTitle();
                        String content = arrayTodos.get(position).getContent();
                        updateIntent.putExtra(EXTRA_MAIN_TITLE, title);
                        updateIntent.putExtra(EXTRA_MAIN_CONTENT, content);
                        startActivityForResult(updateIntent, TEXT_REQUEST_UPDATE);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public void CheckTodo(int isCheck, int id, int position) {
        database.QueryData("UPDATE todo SET finish = '" + isCheck + "'WHERE id = '" + id + "';");
        getDatabase();
        todoAdapter.notifyDataSetChanged();
    }

    public void addFavourite(int isFavourite, int id, int position) {
        database.QueryData("UPDATE todo SET favourite = '" + isFavourite + "'WHERE id = '" + id + "';");
        getDatabase();
        todoAdapter.notifyDataSetChanged();
    }

    public void launchCreateActivity() {
        Intent createIntent = new Intent((MainActivity) getActivity(), CreateActivity.class);
        startActivityForResult(createIntent, TEXT_REQUEST_CREATE);
    }

    public void DialogDeleteTodo(final String title, final int id, int position) {
        AlertDialog.Builder dialogDel = new AlertDialog.Builder(getActivity());
        dialogDel.setMessage("Do you want delete this todo ?");
        dialogDel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM Todo WHERE Id = '" + id + "'");
                Toast.makeText(getActivity(), "Deleted " + title, Toast.LENGTH_SHORT).show();
                arrayTodos.remove(position);
                todoAdapter.notifyDataSetChanged();
            }
        });
        dialogDel.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogDel.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST_CREATE) {
            if (resultCode == RESULT_OK) {
                String title_create = data.getStringExtra(CreateActivity.EXTRA_CREATE_TITLE);
                String content_create = data.getStringExtra(CreateActivity.EXTRA_CREATE_CONTENT);
                //Todo todo = new Todo(1, title_create, content_create, false);
                //arrayTodos.add(todo);
                database.QueryData("INSERT INTO todo VALUES(null, '" + title_create + "', '" + content_create + "', '" + 0 + "', '" + 0 + "'," + 1 + "')");
                getDatabase();
                todoAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == TEXT_REQUEST_UPDATE) {
            if (resultCode == RESULT_OK) {
                String title_update = data.getStringExtra(UpdateActivity.EXTRA_UPDATE_TITLE);
                String content_update = data.getStringExtra(UpdateActivity.EXTRA_UPDATE_CONTENT);
                //arrayTodos.get(index).setTitle(title_update);
                //arrayTodos.get(index).setContent(content_update);
                database.QueryData("UPDATE todo SET title = '" + title_update + "', content = '" + content_update + "' WHERE id = '" + index + "';");
                getDatabase();
                todoAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.all:
                arrayTodos.clear();
                getDatabase();
                Toast.makeText(getActivity(), "All", Toast.LENGTH_SHORT).show();
                break;
            case R.id.unfinished:
                arrayTodos.clear();
                String unfinished = "SELECT todo.id, title, content, finish, favourite, time, location, topic.topicname, topic.id FROM todo, topic WHERE todo.topic = topic.id AND finish = 0 AND favourite = 1";
                queryTodos(unfinished);
                Toast.makeText(getActivity(), "Unfinished", Toast.LENGTH_SHORT).show();
                break;
            case R.id.finished:
                arrayTodos.clear();
                String finished = "SELECT todo.id, title, content, finish, favourite, time, location, topic.topicname, topic.id FROM todo, topic WHERE todo.topic = topic.id AND finish = 1 AND favourite = 1";
                queryTodos(finished);
                Toast.makeText(getActivity(), "Finished", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void queryTodos(String sql) {
        arrayTodos.clear();
        Cursor queryTodo = database.GetData(sql);
        while (queryTodo.moveToNext()) {
            int id = queryTodo.getInt(0);
            String title = queryTodo.getString(1);
            String content = queryTodo.getString(2);
            boolean finish = (queryTodo.getInt(3) == 1);
            boolean favourite = (queryTodo.getInt(4) == 1);
            String time = queryTodo.getString(5);
            String location = queryTodo.getString(6);
            String topic = queryTodo.getString(7);
            int topic_id = queryTodo.getInt(8);
            Todo todo = new Todo(id, title, content, finish, favourite, time, location, topic, topic_id);
            arrayTodos.add(todo);
        }
        todoAdapter.notifyDataSetChanged();
    }
}