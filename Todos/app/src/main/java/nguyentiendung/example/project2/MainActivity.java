package nguyentiendung.example.project2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

public class MainActivity extends AppCompatActivity {
    Database database;
    public final static String EXTRA_MAIN_TITLE = ".project2.example.EXTRA_MAIN_TITLE";
    public final static String EXTRA_MAIN_CONTENT = ".project2.example.EXTRA_MAIN_CONTENT";
    final static int TEXT_REQUEST_CREATE = 2;
    final static int TEXT_REQUEST_UPDATE = 3;
    int index = -1;
    ListView lvTodos;
    ArrayList<Todo> arrayTodos;
    TodoAdapter todoAdapter;
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reference();
        todoAdapter = new TodoAdapter(this, R.layout.todo_line, arrayTodos);
        lvTodos.setAdapter(todoAdapter);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayTodos.clear();
                Cursor dataTodoList = database.GetData("SELECT * FROM Todo WHERE Finish = 1");
                while (dataTodoList.moveToNext()) {
                    int id = dataTodoList.getInt(0);
                    String title = dataTodoList.getString(1);
                    String content = dataTodoList.getString(2);
                    int finish = dataTodoList.getInt(3);
                    Todo todo = new Todo(id, title, content, finish);
                    arrayTodos.add(todo);
                }
                todoAdapter.notifyDataSetChanged();
            }
        });
        getDatabase();
        todoAdapter.notifyDataSetChanged();
    };

    public void launchCreateActivity(View view) {
        Intent createIntent = new Intent(MainActivity.this, CreateActivity.class);
        startActivityForResult(createIntent, TEXT_REQUEST_CREATE);
    };

    public void showMenuLine(View view, int id, int position) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_line, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.delele:
                        DialogDeleteTodo(arrayTodos.get(position).getTitle(), id, position);
                        break;
                    case R.id.update:
                        index = arrayTodos.get(position).getId();
                        Intent updateIntent = new Intent(MainActivity.this, UpdateActivity.class);
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
    public void reference() {
        lvTodos = (ListView) findViewById(R.id.listview_main);
        arrayTodos = new ArrayList<>();
    };
    public void getDatabase() {
        arrayTodos.clear();
        //Create database todolist
        database = new Database(this, "todolist.sqlite", null, 1);
        //Create table todo
        database.QueryData("CREATE TABLE IF NOT EXISTS Todo(Id INTEGER PRIMARY KEY AUTOINCREMENT, Title VARCHAR(200), Content VARCHAR(200), Finish INTEGER)");

        //database.QueryData("DROP TABLE Todo");
        Cursor dataTodoList = database.GetData("SELECT * FROM Todo");
        while (dataTodoList.moveToNext()) {
            int id = dataTodoList.getInt(0);
            String title = dataTodoList.getString(1);
            String content = dataTodoList.getString(2);
            int finish = dataTodoList.getInt(3);
            Todo todo = new Todo(id, title, content, finish);
            arrayTodos.add(todo);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.all:
                getDatabase();
                todoAdapter.notifyDataSetChanged();
                break;
            case R.id.unfinished:
                Toast.makeText(MainActivity.this, "unfinised", Toast.LENGTH_SHORT).show();
                arrayTodos.clear();
                Cursor dataTodoListUnfi = database.GetData("SELECT * FROM Todo WHERE Finish = '0'");
                while (dataTodoListUnfi.moveToNext()) {
                    int id = dataTodoListUnfi.getInt(0);
                    String title = dataTodoListUnfi.getString(1);
                    String content = dataTodoListUnfi.getString(2);
                    int finish = dataTodoListUnfi.getInt(3);
                    Todo todo = new Todo(id, title, content, finish);
                    arrayTodos.add(todo);
                }
                todoAdapter.notifyDataSetChanged();
                break;
            case R.id.finished:
                Toast.makeText(MainActivity.this, "finised", Toast.LENGTH_SHORT).show();
                arrayTodos.clear();
                Cursor dataTodoListFi = database.GetData("SELECT * FROM Todo WHERE Finish = '1'");
                while (dataTodoListFi.moveToNext()) {
                    int id = dataTodoListFi.getInt(0);
                    String title = dataTodoListFi.getString(1);
                    String content = dataTodoListFi.getString(2);
                    int finish = dataTodoListFi.getInt(3);
                    Log.d("DDUNG", "onOptionsItemSelected: " + title + " " + content + " " + finish);
                    Todo todo = new Todo(id, title, content, finish);
                    arrayTodos.add(todo);
                }
                todoAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void DialogDeleteTodo(final String title, final int id, int position) {
        AlertDialog.Builder dialogDel = new AlertDialog.Builder(this);
        dialogDel.setMessage("Do you want delete this todo ?");
        dialogDel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM Todo WHERE Id = '" + id + "'");
                Toast.makeText(MainActivity.this, "Deleted " + title, Toast.LENGTH_SHORT).show();
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

    public void CheckTodo(int isCheck, final int id, int position) {
        database.QueryData("UPDATE Todo SET Finish = '" + isCheck + "' WHERE Id = '" + id + "';");
        getDatabase();
        todoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST_CREATE) {
            if (resultCode == RESULT_OK) {
                String title_create = data.getStringExtra(CreateActivity.EXTRA_CREATE_TITLE);
                String content_create = data.getStringExtra(CreateActivity.EXTRA_CREATE_CONTENT);
                database.QueryData("INSERT INTO Todo VALUES(null, '" + title_create + "', '" + content_create + "', '" + 0 + "')");
                getDatabase();
                todoAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == TEXT_REQUEST_UPDATE) {
            if (resultCode == RESULT_OK) {
                String title_update = data.getStringExtra(UpdateActivity.EXTRA_UPDATE_TITLE);
                String content_update = data.getStringExtra(UpdateActivity.EXTRA_UPDATE_CONTENT);
                database.QueryData("UPDATE Todo SET Title = '" + title_update + "', Content = '" + content_update + "' WHERE Id = '" + index + "';");
                getDatabase();
                todoAdapter.notifyDataSetChanged();
            }
        }
    }
}