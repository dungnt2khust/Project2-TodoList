package nguyentiendung.example.todo_navigation.ui.topic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;

import nguyentiendung.example.todo_navigation.Database;
import nguyentiendung.example.todo_navigation.R;
import nguyentiendung.example.todo_navigation.Todo;
import nguyentiendung.example.todo_navigation.TodoAdapter;

public class TopicFragment extends ListFragment {
    ArrayList<Todo> arrayTodos = new ArrayList<>();
    Database database;
    TodoAdapter todoAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_topic, container, false);
        todoAdapter = new TodoAdapter(getContext(), R.layout.todo_line, arrayTodos, null, TopicFragment.this, null);
        setListAdapter(todoAdapter);
        //getDatabase();
        return root;
    }/*
    public void getDatabase() {
        arrayTodos.clear();
        database = new Database(getActivity(), "todolist.sqlite", null, 1);
        //Create table todo
        //database.QueryData("CREATE TABLE IF NOT EXISTS topic(id INTEGER PRIMARY KEY AUTOINCREMENT, topicname VARCHAR(200)");
        //database.QueryData("CREATE TABLE IF NOT EXISTS todo(id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(200), content VARCHAR(200), finish BOOLEAN, favourite BOOLEAN, topic INTEGER FOREIGN KEY REFERENCES topic(id)");
        database.QueryData("CREATE TABLE IF NOT EXISTS todo(id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(200), content VARCHAR(200), finish INTEGER)");
        //database.QueryData("DROP TABLE todo");
        //database.QueryData("DROP TABLE topic");
        Cursor dataTodoList = database.GetData("SELECT * FROM todo");
        while (dataTodoList.moveToNext()) {
            int id = dataTodoList.getInt(0);
            String title = dataTodoList.getString(1);
            String content = dataTodoList.getString(2);
            boolean finish = (dataTodoList.getInt(3) == 1);
            Todo todo = new Todo(id, title, content, finish);
            arrayTodos.add(todo);
        }
        todoAdapter.notifyDataSetChanged();
    }
    */
}