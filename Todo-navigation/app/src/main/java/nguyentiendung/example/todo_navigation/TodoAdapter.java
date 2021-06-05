package nguyentiendung.example.todo_navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import nguyentiendung.example.todo_navigation.ui.favourite.FavouriteFragment;
import nguyentiendung.example.todo_navigation.ui.topic.TopicFragment;
import nguyentiendung.example.todo_navigation.ui.home.HomeFragment;

public class TodoAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Todo> todos;
    private HomeFragment fragmentHome;
    private TopicFragment fragmentTopic;
    private FavouriteFragment fragmentFavourite;
    private TopicDetailActivity topicDetailActivity;
    public TodoAdapter(Context context, int layout, List<Todo> todos, HomeFragment fragmentHome, TopicFragment fragmentTopic, FavouriteFragment fragmentFavourite, TopicDetailActivity topicDetailActivity) {
        this.context = context;
        this.layout = layout;
        this.todos = todos;
        this.fragmentHome = fragmentHome;
        this.fragmentTopic = fragmentTopic;
        this.fragmentFavourite = fragmentFavourite;
        this.topicDetailActivity = topicDetailActivity;
    }

    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public Object getItem(int position) {
        return todos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        // reference
        final Todo todoItem = todos.get(position);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.textview_title);
        TextView txtContent = (TextView) convertView.findViewById(R.id.textview_content);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkbox);
        Button btnMenuLine = (Button) convertView.findViewById(R.id.button_menu_line);
        ImageButton imgbtnStar = (ImageButton) convertView.findViewById(R.id.imgbtn_star);

        btnMenuLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentHome != null)
                    fragmentHome.showMenuLine(v, todoItem.getId(), position);
                else if (fragmentFavourite != null)
                    fragmentFavourite.showMenuLine(v, todoItem.getId(), position);
                else if (topicDetailActivity != null)
                    topicDetailActivity.showMenuLine(v, todoItem.getId(), position);
            }
        });

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int check;
                if (isChecked) {
                    check = 1;
                } else {
                    check = 0;
                }
                if (fragmentHome != null)
                    fragmentHome.CheckTodo(check, todoItem.getId(), position);
                else if (fragmentFavourite != null)
                    fragmentFavourite.CheckTodo(check, todoItem.getId(), position);
                else if (topicDetailActivity != null)
                    topicDetailActivity.CheckTodo(check, todoItem.getId(), position);
            }
        });

        imgbtnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFavourite = todoItem.getFavourite();
                int favourite;
                if (isFavourite) {
                    favourite = 0;
                } else {
                    favourite = 1;
                }
                if (fragmentHome != null)
                    fragmentHome.addFavourite(favourite, todoItem.getId(), position);
                else if (fragmentFavourite != null)
                    fragmentFavourite.addFavourite(favourite, todoItem.getId(), position);
                else if (topicDetailActivity != null)
                    topicDetailActivity.addFavourite(favourite, todoItem.getId(), position);
            }
        });
       // assign value
        Todo todo = todos.get(position);
        txtTitle.setText(todo.getTitle());
        txtContent.setText(todo.getContent());
        cb.setChecked(todo.getCheck());
        if (todo.getFavourite()) {
            imgbtnStar.setImageResource(R.drawable.starclicked);
        } else {
            imgbtnStar.setImageResource(R.drawable.star);
        }
        return convertView;
    }
}
