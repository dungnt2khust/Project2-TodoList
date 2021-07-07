package nguyentiendung.example.todo_navigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import nguyentiendung.example.todo_navigation.ui.home.HomeFragment;
import nguyentiendung.example.todo_navigation.ui.home.HomeViewModel;
import nguyentiendung.example.todo_navigation.ui.topic.TopicFragment;

public class TopicDetailActivity extends AppCompatActivity {
    public final static String EXTRA_MAIN_TITLE = ".project2.example.EXTRA_MAIN_TITLE";
    public final static String EXTRA_MAIN_CONTENT = ".project2.example.EXTRA_MAIN_CONTENT";
    public final static String EXTRA_MAIN_TIME = ".project2.example.EXTRA_MAIN_TIME";
    public final static String EXTRA_MAIN_LOCATION = ".project2.example.EXTRA_MAIN_LOCATION";
    public final static String EXTRA_MAIN_TOPIC = ".project2.example.EXTRA_MAIN_TOPIC";
    public final static String EXTRA_MAIN_DATE = ".project2.example.EXTRA_MAIN_DATE";
    final static int TEXT_REQUEST_CREATE = 2;
    final static int TEXT_REQUEST_UPDATE = 3;
    int index = -1;
    ArrayList<Todo> arrayTodos = new ArrayList<>();
    private HomeViewModel homeViewModel;
    Database database;
    FloatingActionButton fabNew;
    TodoAdapter todoAdapter;
    ListView lvTopic;
    TextView txtTopicName;
    Intent topicIntent;
    int topic_id;
    String topic_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);
        fabNew = (FloatingActionButton) findViewById(R.id.fab_new_topic);
        lvTopic = (ListView) findViewById(R.id.listview_topic);
        txtTopicName = (TextView) findViewById(R.id.textview_topic_name);
        topicIntent = getIntent();
        topic_id = topicIntent.getIntExtra(TopicFragment.EXTRA_ID_TOPIC, 0);
        topic_name = topicIntent.getStringExtra(TopicFragment.EXTRA_NAME_TOPIC);
        txtTopicName.setText(topic_name);
        todoAdapter = new TodoAdapter(this, R.layout.todo_line, arrayTodos, null, null, null, TopicDetailActivity.this);
        lvTopic.setAdapter(todoAdapter);
        getDatabase();
        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateActivity();
            }
        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_200)));
    }
    public void getDatabase() {
        arrayTodos.clear();
        //database = mainActivity.getDatabase();
        database = new Database(TopicDetailActivity.this, "todolist.sqlite", null, 1);
        //Create table todo
        database.QueryData("CREATE TABLE IF NOT EXISTS topic(id INTEGER PRIMARY KEY AUTOINCREMENT, topicname VARCHAR(200) UNIQUE)");
        database.QueryData("CREATE TABLE IF NOT EXISTS todo(id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(200), content VARCHAR(200), finish INTEGER, favourite INTEGER, time VARCHAR(200), location VARCHAR(200), topic INTEGER, FOREIGN KEY(topic) REFERENCES topic(id) ON DELETE CASCADE ON UPDATE CASCADE)");
        String getDatabase = "SELECT todo.id, title, content, finish, favourite, date, location, time, topic.topicname, topic.id FROM todo, topic WHERE todo.topic = topic.id AND topic.id = '" + topic_id + "'";
        queryTodos(getDatabase);
    }

    public void showMenuLine(View view, int id, int position) {
        PopupMenu popupMenu = new PopupMenu(TopicDetailActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_line, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.delete:
                        DialogDeleteTodo(arrayTodos.get(position).getTitle(), id, position);
                        break;
                    case R.id.update:
                        index = arrayTodos.get(position).getId();
                        Intent updateIntent = new Intent(TopicDetailActivity.this, UpdateActivity.class);
                        String title = arrayTodos.get(position).getTitle();
                        String content = arrayTodos.get(position).getContent();
                        String topic = arrayTodos.get(position).getTopic();
                        String time = arrayTodos.get(position).getTime();
                        String location = arrayTodos.get(position).getLocation();
                        String date = arrayTodos.get(position).getDate();
                        updateIntent.putExtra(EXTRA_MAIN_TITLE, title);
                        updateIntent.putExtra(EXTRA_MAIN_CONTENT, content);
                        updateIntent.putExtra(EXTRA_MAIN_TOPIC, topic);
                        updateIntent.putExtra(EXTRA_MAIN_TIME, time);
                        updateIntent.putExtra(EXTRA_MAIN_LOCATION, location);
                        updateIntent.putExtra(EXTRA_MAIN_DATE, date);
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
        Intent createIntent = new Intent(TopicDetailActivity.this, CreateActivity.class);
        startActivityForResult(createIntent, TEXT_REQUEST_CREATE);
    }
    public void DialogDeleteTodo(final String title, final int id, int position) {
        AlertDialog.Builder dialogDel = new AlertDialog.Builder(TopicDetailActivity.this);
        dialogDel.setMessage("Do you want delete this todo ?");
        dialogDel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM Todo WHERE Id = '" + id + "'");
                Toast.makeText(TopicDetailActivity.this, "Deleted " + title, Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.all:
                arrayTodos.clear();
                getDatabase();
                Toast.makeText(TopicDetailActivity.this, "All", Toast.LENGTH_SHORT).show();
                break;
            case R.id.unfinished:
                arrayTodos.clear();
                String unfinished = "SELECT todo.id, title, content, finish, favourite, time, location, topic.topicname, topic.id FROM todo, topic WHERE todo.topic = topic.id AND finish = 0 AND topic.topicname = '" + topic_name + "'";
                queryTodos(unfinished);
                Toast.makeText(TopicDetailActivity.this, "Unfinished", Toast.LENGTH_SHORT).show();
                break;
            case R.id.finished:
                arrayTodos.clear();
                String finished = "SELECT todo.id, title, content, finish, favourite, time, location, topic.topicname, topic.id FROM todo, topic WHERE todo.topic = topic.id AND finish = 1 AND topic.topicname = '" + topic_name + "'";
                queryTodos(finished);
                Toast.makeText(TopicDetailActivity.this, "Finished", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST_CREATE) {
            if (resultCode == RESULT_OK) {
                String title_create = data.getStringExtra(CreateActivity.EXTRA_CREATE_TITLE);
                String content_create = data.getStringExtra(CreateActivity.EXTRA_CREATE_CONTENT);
                String topic_create = data.getStringExtra(CreateActivity.EXTRA_CREATE_TOPIC);
                String date_create = data.getStringExtra(CreateActivity.EXTRA_CREATE_DATE);
                String location_create = data.getStringExtra(CreateActivity.EXTRA_CREATE_LOCATION);
                String time_create = data.getStringExtra(CreateActivity.EXTRA_CREATE_TIME);
                if (topic_create == "default" || topic_create.length() == 0) {
                    database.QueryData("INSERT INTO todo VALUES(null, '" + title_create + "', '" + content_create + "', '" + 0 + "', '" + 0 + "', '" + date_create + "', '" + location_create + "', '" + time_create + "', '" + 1 + "')");
                } else {
                    Cursor checkExistTopic = database.GetData("SELECT id FROM topic WHERE topicname = '" + topic_create + "'");
                    int id_topic = -1;
                    while (checkExistTopic.moveToNext()) {
                        id_topic = checkExistTopic.getInt(0);
                    }
                    if (id_topic > 0) {
                        database.QueryData("INSERT INTO todo VALUES(null, '" + title_create + "', '" + content_create + "', '" + 0 + "', '" + 0 + "', '" + date_create + "', '" + location_create + "', '" + time_create + "', '" + id_topic + "')");
                    } else {
                        database.QueryData("INSERT INTO topic VALUES(null, '" + topic_create + "')");
                        Cursor getIdTopic = database.GetData("SELECT id FROM topic WHERE topicname = '" + topic_create + "'");
                        int id_topic_2 = -1;
                        while (getIdTopic.moveToNext()) {
                            id_topic_2 = getIdTopic.getInt(0);
                        }
                        database.QueryData("INSERT INTO todo VALUES(null, '" + title_create + "', '" + content_create + "', '" + 0 + "', '" + 0 + "', '" + date_create + "', '" + location_create + "', '" + time_create + "', '" + id_topic_2 + "')");
                    }

                }
                getDatabase();
                todoAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == TEXT_REQUEST_UPDATE) {
            if (resultCode == RESULT_OK) {
                String title_update = data.getStringExtra(UpdateActivity.EXTRA_UPDATE_TITLE);
                String content_update = data.getStringExtra(UpdateActivity.EXTRA_UPDATE_CONTENT);
                String time_update = data.getStringExtra(UpdateActivity.EXTRA_UPDATE_TIME);
                String location_update = data.getStringExtra(UpdateActivity.EXTRA_UPDATE_LOCATION);
                String date_update = data.getStringExtra(UpdateActivity.EXTRA_UPDATE_DATE);
                database.QueryData("UPDATE todo SET title = '" + title_update + "', content = '" + content_update + "', time = '" + time_update + "', location = '" + location_update + "', date = '" + date_update + "'  WHERE id = '" + index + "';");
                getDatabase();
                todoAdapter.notifyDataSetChanged();
            }
        }
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
            String date = queryTodo.getString(5);
            String location = queryTodo.getString(6);
            String time = queryTodo.getString(7) ;
            String topic = queryTodo.getString(8);
            int topic_id = queryTodo.getInt(9);
            Todo todo = new Todo(id, title, content, finish, favourite, date, location, time, topic, topic_id);
            arrayTodos.add(todo);
        }
        todoAdapter.notifyDataSetChanged();
    }
}