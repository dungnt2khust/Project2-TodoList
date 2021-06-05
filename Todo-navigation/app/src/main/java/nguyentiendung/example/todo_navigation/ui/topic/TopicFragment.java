package nguyentiendung.example.todo_navigation.ui.topic;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.ListFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import nguyentiendung.example.todo_navigation.CreateActivity;
import nguyentiendung.example.todo_navigation.CreateTopic;
import nguyentiendung.example.todo_navigation.Database;
import nguyentiendung.example.todo_navigation.MainActivity;
import nguyentiendung.example.todo_navigation.R;
import nguyentiendung.example.todo_navigation.Todo;
import nguyentiendung.example.todo_navigation.TodoAdapter;
import nguyentiendung.example.todo_navigation.Topic;
import nguyentiendung.example.todo_navigation.TopicAdapter;
import nguyentiendung.example.todo_navigation.TopicDetailActivity;
import nguyentiendung.example.todo_navigation.UpdateActivity;

import static android.app.Activity.RESULT_OK;

public class TopicFragment extends ListFragment {
    public final static String EXTRA_NAME_TOPIC = ".project2.example.EXTRA_NAME_TOPIC";
    public final static String EXTRA_ID_TOPIC = ".project2.example.EXTRA_ID_TOPIC";
    final static int TEXT_REQUEST_UPDATE_TOPIC = 4;
    final static int TEXT_REQUEST_CREATE_TOPIC = 5;
    FloatingActionButton fabNew;
    ArrayList<Topic> arrayTopics = new ArrayList<>();
    Database database;
    int index = -1;
    TopicAdapter topicAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_topic, container, false);
        topicAdapter = new TopicAdapter(getContext(), R.layout.topic_line, arrayTopics, TopicFragment.this);
        setListAdapter(topicAdapter);
        getDatabase();
        return root;
    }
    public void getDatabase() {
        arrayTopics.clear();
        database = ((MainActivity)getActivity()).getDatabase();
        Cursor dataTodoList = database.GetData("SELECT * FROM topic");
        while (dataTodoList.moveToNext()) {
            int topic_id = dataTodoList.getInt(0);
            String topic_name = dataTodoList.getString(1);
            Topic topic = new Topic(topic_id, topic_name);
            arrayTopics.add(topic);
        }
        topicAdapter.notifyDataSetChanged();
    }
    public void launchCreateTopicActivity() {
        Intent createIntent = new Intent((MainActivity)getActivity(), CreateTopic.class);
        startActivityForResult(createIntent, TEXT_REQUEST_CREATE_TOPIC);
    }
    public void showMenuLine(View view, int id, int position) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_line, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.delete:
                        if (id != 1)
                            DialogDeleteTodo(arrayTopics.get(position).getTopic_name(), id, position);
                        else
                            Toast.makeText(getContext(), "You can not delete default topic.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.update:
                        index = arrayTopics.get(position).getTopic_id();
                        Intent topicIntent = new Intent(getContext(), TopicDetailActivity.class);
                        int topic_id = arrayTopics.get(position).getTopic_id();
                        String topic_name = arrayTopics.get(position).getTopic_name();
                        topicIntent.putExtra(EXTRA_ID_TOPIC, topic_id);
                        topicIntent.putExtra(EXTRA_NAME_TOPIC, topic_name);
                        startActivity(topicIntent);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
    public void DialogDeleteTodo(final String title, final int id, int position) {
        AlertDialog.Builder dialogDel = new AlertDialog.Builder(getActivity());
        dialogDel.setMessage("Do you want delete this topic ?");
        dialogDel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM topic WHERE id = '" + id + "'");
                Toast.makeText(getActivity(), "Deleted " + title, Toast.LENGTH_SHORT).show();
                arrayTopics.remove(position);
                topicAdapter.notifyDataSetChanged();
            }
        });
        dialogDel.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogDel.show();
    }
}