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

public class TopicAdapterSpinner extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Topic> topics;

    public TopicAdapterSpinner(Context context, int layout, List<Topic> topics) {
        this.context = context;
        this.layout = layout;
        this.topics = topics;
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
        TextView txtTopicSpinner = (TextView) convertView.findViewById(R.id.title_topic_spinner);
        final Topic topic = topics.get(position);
        txtTopicSpinner.setText(topic.getTopic_name());
        return convertView;
    }
}
