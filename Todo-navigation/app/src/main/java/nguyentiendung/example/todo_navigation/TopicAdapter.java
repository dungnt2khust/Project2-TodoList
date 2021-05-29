package nguyentiendung.example.todo_navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import nguyentiendung.example.todo_navigation.ui.topic.TopicFragment;

public class TopicAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Topic> topics;
    TopicFragment topicFragment;

    public TopicAdapter(Context context, int layout, List<Topic> topics, TopicFragment topicFragment) {
        this.context = context;
        this.layout = layout;
        this.topics = topics;
        this.topicFragment = topicFragment;
    }

    @Override
    public int getCount() {
        return topics.size();
    }

    @Override
    public Object getItem(int position) {
        return topics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);
        TextView txtTopic = (TextView) convertView.findViewById(R.id.title_topic);
        Button btnMenuLine = (Button) convertView.findViewById(R.id.button_menu_line);
        final Topic topic = topics.get(position);
        btnMenuLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicFragment.showMenuLine(v, topic.getTopic_id(), position);
            }
        });
        txtTopic.setText(topic.getTopic_name());
        return convertView;
    }
}
