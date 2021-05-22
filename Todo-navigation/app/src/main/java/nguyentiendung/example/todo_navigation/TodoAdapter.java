package nguyentiendung.example.todo_navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import nguyentiendung.example.todo_navigation.ui.home.HomeFragment;

public class TodoAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Todo> todos;
    private HomeFragment fragment;

    public TodoAdapter(Context context, int layout, List<Todo> todos, HomeFragment fragment) {
        this.context = context;
        this.layout = layout;
        this.todos = todos;
        this.fragment = fragment;
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
        btnMenuLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.showMenuLine(v, todoItem.getId(), position);
            }
        });
       // assign value
        Todo todo = todos.get(position);
        txtTitle.setText(todo.getTitle());
        txtContent.setText(todo.getContent());
        cb.setChecked(todo.getCheck());
        return convertView;
    }
}
