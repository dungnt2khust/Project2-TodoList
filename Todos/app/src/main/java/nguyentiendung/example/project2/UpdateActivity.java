package nguyentiendung.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    public final static String EXTRA_UPDATE_TITLE = ".project2.example.EXTRA_UPDATE_TITLE";
    public final static String EXTRA_UPDATE_CONTENT = ".project2.example.EXTRA_UPDATE_CONTENT";
    Intent updateIntent;
    private EditText edtTitle, edtContent;
    private Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        reference();
        updateIntent = getIntent();
        edtTitle.setText(updateIntent.getStringExtra(MainActivity.EXTRA_MAIN_TITLE));
        edtContent.setText(updateIntent.getStringExtra(MainActivity.EXTRA_MAIN_CONTENT));
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateATodo();
            }
        });
    }

    public void reference() {
        edtTitle = (EditText) findViewById(R.id.edittext_title_update);
        edtContent = (EditText) findViewById(R.id.edittext_content_update);
        btnUpdate = (Button) findViewById(R.id.button_update);
    }

    public void updateATodo() {
        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        if (title.isEmpty() == false) {
            content = (content.isEmpty() == true) ? "" : content;
            intent.putExtra(EXTRA_UPDATE_TITLE, title);
            intent.putExtra(EXTRA_UPDATE_CONTENT, content);
            setResult(RESULT_OK, intent);
            finish();
        }
        else {
            Toast.makeText(UpdateActivity.this, "Not allow empty title", Toast.LENGTH_SHORT).show();
        }
    }
}