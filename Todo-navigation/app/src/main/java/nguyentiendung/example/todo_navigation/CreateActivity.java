package nguyentiendung.example.todo_navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity {
    public final static String EXTRA_CREATE_TITLE = ".project2.example.EXTRA_CREATE_TITLE";
    public final static String EXTRA_CREATE_CONTENT = ".project2.example.EXTRA_CREATE_CONTENT";
    public final static String EXTRA_CREATE_TOPIC = ".project2.example.EXTRA_CREATE_TOPIC";
    private EditText edtTitle, edtContent, edtTopic;
    private Button btnCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        reference();
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createATodo();
            }
        });
    }

    public void reference() {
        edtTitle = (EditText) findViewById(R.id.edittext_title_create);
        edtContent = (EditText) findViewById(R.id.edittext_content_create);
        edtTopic = (EditText) findViewById(R.id.edittext_topic_create);
        btnCreate = (Button) findViewById(R.id.button_create);
    }
    public void createATodo() {
        Intent createIntent = new Intent(CreateActivity.this, MainActivity.class);
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        String topic = edtTopic.getText().toString();
        if (title.isEmpty() == false) {
            content = (content.isEmpty() == true) ? "Empty content" : content;
            createIntent.putExtra(EXTRA_CREATE_TITLE, title);
            createIntent.putExtra(EXTRA_CREATE_CONTENT, content);
            createIntent.putExtra(EXTRA_CREATE_TOPIC, topic);
            setResult(RESULT_OK, createIntent);
            finish();
        }
        else {
            Toast.makeText(CreateActivity.this, "Not allow empty title", Toast.LENGTH_SHORT).show();
        }
    }
}