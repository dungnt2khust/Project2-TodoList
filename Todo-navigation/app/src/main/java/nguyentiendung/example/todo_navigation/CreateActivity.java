package nguyentiendung.example.todo_navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateActivity extends AppCompatActivity {
    public final static String EXTRA_CREATE_TITLE = ".project2.example.EXTRA_CREATE_TITLE";
    public final static String EXTRA_CREATE_CONTENT = ".project2.example.EXTRA_CREATE_CONTENT";
    public final static String EXTRA_CREATE_TOPIC = ".project2.example.EXTRA_CREATE_TOPIC";
    public final static String EXTRA_CREATE_TIME = ".project2.example.EXTRA_CREATE_TIME";
    public final static String EXTRA_CREATE_LOCATION = ".project2.example.EXTRA_CREATE_LOCATION";
    private EditText edtTitle, edtContent, edtTopic, edtTime, edtLocation;
    private ImageButton imgbtnTime;
    private Button btnCreate;
    ArrayList<Topic> arrayTopics = new ArrayList<>();
    Database database;
    TopicAdapterSpinner topicAdapterSpinner;
    Spinner spinnerTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        reference();
        topicAdapterSpinner = new TopicAdapterSpinner(CreateActivity.this, R.layout.topic_line_spinner, arrayTopics);
        this.spinnerTopic.setAdapter(topicAdapterSpinner);
        getDatabase();
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createATodo();
            }
        });
        imgbtnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateActivity.this, "Pick a date", Toast.LENGTH_SHORT).show();
                pickADate();
            }
        });

        spinnerTopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtTopic.setText(arrayTopics.get(position).getTopic_name());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                edtTopic.setText("default");
            }
        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_200)));
    }

    public void reference() {
        edtTitle = (EditText) findViewById(R.id.edittext_title_create);
        edtContent = (EditText) findViewById(R.id.edittext_content_create);
        edtTopic = (EditText) findViewById(R.id.edittext_topic_create);
        edtTime = (EditText) findViewById(R.id.edittext_time_create);
        edtLocation = (EditText) findViewById(R.id.edittext_location_create);
        btnCreate = (Button) findViewById(R.id.button_create);
        imgbtnTime = (ImageButton) findViewById(R.id.imageButton_time_create);
        spinnerTopic = (Spinner) findViewById(R.id.spinner_topic_create);
    }

    public void getDatabase() {
        arrayTopics.clear();
        database = new Database(CreateActivity.this, "todolist.sqlite", null, 1);
        //Create table todo
        database.QueryData("CREATE TABLE IF NOT EXISTS topic(id INTEGER PRIMARY KEY AUTOINCREMENT, topicname VARCHAR(200) UNIQUE)");
        database.QueryData("CREATE TABLE IF NOT EXISTS todo(id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(200), content VARCHAR(200), finish INTEGER, favourite INTEGER, time VARCHAR(200), location VARCHAR(200), topic INTEGER, FOREIGN KEY(topic) REFERENCES topic(id) ON DELETE CASCADE ON UPDATE CASCADE)");
        Cursor dataTodoList = database.GetData("SELECT * FROM topic");
        while (dataTodoList.moveToNext()) {
            int topic_id = dataTodoList.getInt(0);
            String topic_name = dataTodoList.getString(1);
            Topic topic = new Topic(topic_id, topic_name);
            arrayTopics.add(topic);
        }
        topicAdapterSpinner.notifyDataSetChanged();
    }
    public void createATodo() {
        Intent createIntent = new Intent(CreateActivity.this, MainActivity.class);
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        String topic = edtTopic.getText().toString();
        String time = edtTime.getText().toString();
        String location = edtLocation.getText().toString();
        if (title.isEmpty() == false) {
            content = (content.isEmpty() == true) ? "Empty content" : content;
            createIntent.putExtra(EXTRA_CREATE_TITLE, title);
            createIntent.putExtra(EXTRA_CREATE_CONTENT, content);
            createIntent.putExtra(EXTRA_CREATE_TOPIC, topic);
            createIntent.putExtra(EXTRA_CREATE_TIME, time);
            createIntent.putExtra(EXTRA_CREATE_LOCATION, location);
            setResult(RESULT_OK, createIntent);
            finish();
        }
        else {
            Toast.makeText(CreateActivity.this, "Not allow empty title", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickADate() {
        Calendar calendar = Calendar.getInstance();
        int dayz = calendar.get(Calendar.DATE);
        int monthz = calendar.get(Calendar.MONTH);
        int yearz = calendar.get(Calendar.YEAR);
        Log.d("DDUNG", "pickADate: " + dayz + " " + monthz + " " + yearz);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, yearz, monthz, dayz);
        datePickerDialog.show();
    }
}