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
    private EditText edtTitle, edtContent;
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
        btnCreate = (Button) findViewById(R.id.button_create);
    }
    public void createATodo() {
        Intent secondIntent = new Intent(CreateActivity.this, MainActivity.class);
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        if (title.isEmpty() == false) {
            content = (content.isEmpty() == true) ? "Empty content" : content;
            secondIntent.putExtra(EXTRA_CREATE_TITLE, title);
            secondIntent.putExtra(EXTRA_CREATE_CONTENT, content);
            setResult(RESULT_OK, secondIntent);
            finish();
        }
        else {
            Toast.makeText(CreateActivity.this, "Not allow empty title", Toast.LENGTH_SHORT).show();
        }
    }
}