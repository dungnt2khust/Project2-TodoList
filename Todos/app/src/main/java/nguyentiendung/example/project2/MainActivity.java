package nguyentiendung.example.project2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MAIN_TITLE = ".project2.example.EXTRA_MAIN_TITLE";
    public final static String EXTRA_MAIN_CONTENT = ".project2.example.EXTRA_MAIN_CONTENT";
    final static int TEXT_REQUEST_CREATE = 2;
    final static int TEXT_REQUEST_UPDATE = 3;
    SharedPreferences sp;
    int index = -1;
    ListView lvTodos;
    ArrayList<Todo> arrayTodos;
    TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reference();
        lvTodos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                Intent updateIntent = new Intent(MainActivity.this, UpdateActivity.class);
                String title = arrayTodos.get(position).getTitle();
                String content = arrayTodos.get(position).getContent();
                updateIntent.putExtra(EXTRA_MAIN_TITLE, title);
                updateIntent.putExtra(EXTRA_MAIN_CONTENT, content);
                startActivityForResult(updateIntent, TEXT_REQUEST_UPDATE);
            }
        });
        sp = getSharedPreferences("TodoList", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sp.getString("Todos", "");
        if (json.isEmpty()) {
            Toast.makeText(MainActivity.this, "You don't have any todo", Toast.LENGTH_SHORT).show();
        } else {
            Type type = new TypeToken<ArrayList<Todo>>(){}.getType();
            arrayTodos = gson.fromJson(json, type);
        }

        todoAdapter = new TodoAdapter(this, R.layout.todo_line, arrayTodos);
        lvTodos.setAdapter(todoAdapter);
    };

    public void launchCreateActivity(View view) {
        Intent createIntent = new Intent(MainActivity.this, CreateActivity.class);
        startActivityForResult(createIntent, TEXT_REQUEST_CREATE);
    };

    public void reference() {
        lvTodos = (ListView) findViewById(R.id.listview_main);
        arrayTodos = new ArrayList<>();
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST_CREATE) {
            if (resultCode == RESULT_OK) {
                String title_create = data.getStringExtra(CreateActivity.EXTRA_CREATE_TITLE);
                String content_create = data.getStringExtra(CreateActivity.EXTRA_CREATE_CONTENT);
                Todo todo = new Todo(title_create, content_create);
                arrayTodos.add(todo);
                todoAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == TEXT_REQUEST_UPDATE) {
            if (resultCode == RESULT_OK) {
                String title_update = data.getStringExtra(UpdateActivity.EXTRA_UPDATE_TITLE);
                String content_update = data.getStringExtra(UpdateActivity.EXTRA_UPDATE_CONTENT);
                arrayTodos.get(index).setTitle(title_update);
                arrayTodos.get(index).setContent(content_update);
                todoAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayTodos);
        editor.putString("Todos", json);
        editor.apply();
    }
}