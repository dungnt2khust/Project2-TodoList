package nguyentiendung.example.project2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

public class TodoAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<Todo> todos;

    public TodoAdapter(MainActivity context, int layout, List<Todo> todos) {
        this.context = context;
        this.layout = layout;
        this.todos = todos;
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
        TextView txtTitle = (TextView) convertView.findViewById(R.id.textview_title);
        TextView txtContent = (TextView) convertView.findViewById(R.id.textview_content);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkbox);
        Button btnMenuLine = (Button) convertView.findViewById(R.id.button_menu_line);

        final Todo todoItem = todos.get(position);
        btnMenuLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.showMenuLine(v, todoItem.getId(), position);
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
                context.CheckTodo(check, todoItem.getId(), position);
            }
        });
        // assign value
        Todo todo = todos.get(position);
        txtTitle.setText(todo.getTitle());
        txtContent.setText(todo.getContent());
        cb.setChecked(todo.getCheck() == 1);
        return convertView;
    }
}
