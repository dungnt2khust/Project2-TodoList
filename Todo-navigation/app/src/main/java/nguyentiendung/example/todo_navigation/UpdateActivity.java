package nguyentiendung.example.todo_navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nguyentiendung.example.todo_navigation.ui.home.HomeFragment;

public class UpdateActivity extends AppCompatActivity {
    public final static String EXTRA_UPDATE_TITLE = ".project2.example.EXTRA_UPDATE_TITLE";
    public final static String EXTRA_UPDATE_CONTENT = ".project2.example.EXTRA_UPDATE_CONTENT";
    public final static String EXTRA_UPDATE_TIME = ".project2.example.EXTRA_UPDATE_TIME";
    public final static String EXTRA_UPDATE_LOCATION = ".project2.example.EXTRA_UPDATE_LOCATION";
    Intent updateIntent;
    TextView txtTopicName;
    private EditText edtTitle, edtContent, edtTime, edtLocation;
    private Button btnUpdate;
    private ImageButton imgbtnGoogleMap, imgbtnTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        MainActivity mainActivity = new MainActivity();
        reference();
        updateIntent = getIntent();

        edtTitle.setText(updateIntent.getStringExtra(HomeFragment.EXTRA_MAIN_TITLE));
        edtContent.setText(updateIntent.getStringExtra(HomeFragment.EXTRA_MAIN_CONTENT));
        edtTime.setText(updateIntent.getStringExtra(HomeFragment.EXTRA_MAIN_TIME));
        edtLocation.setText(updateIntent.getStringExtra(HomeFragment.EXTRA_MAIN_LOCATION));
        txtTopicName.setText(updateIntent.getStringExtra(HomeFragment.EXTRA_MAIN_TOPIC));

        String loc = edtLocation.getText().toString();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateATodo();
            }
        });
        imgbtnGoogleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLocation(v);
            }
        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_200)));
        imgbtnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateActivity.this, "Pick a date", Toast.LENGTH_SHORT).show();
                pickADate();
            }
        });
    }

    public void reference() {
        edtTitle = (EditText) findViewById(R.id.edittext_title_update);
        edtContent = (EditText) findViewById(R.id.edittext_content_update);
        edtTime = (EditText) findViewById(R.id.edittext_time_update);
        edtLocation = (EditText) findViewById(R.id.edittext_location_update);
        txtTopicName = (TextView) findViewById(R.id.textview_topic_name_update);
        imgbtnGoogleMap = (ImageButton) findViewById(R.id.imageButton_googlemap);
        imgbtnTime = (ImageButton) findViewById(R.id.imageButton_time_update);
        btnUpdate = (Button) findViewById(R.id.button_update);
    }

    public void updateATodo() {
        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        String time = edtTime.getText().toString();
        String location = edtLocation.getText().toString();
        if (title.isEmpty() == false) {
            content = (content.isEmpty() == true) ? "" : content;
            intent.putExtra(EXTRA_UPDATE_TITLE, title);
            intent.putExtra(EXTRA_UPDATE_CONTENT, content);
            intent.putExtra(EXTRA_UPDATE_TIME, time);
            intent.putExtra(EXTRA_UPDATE_LOCATION, location);
            setResult(RESULT_OK, intent);
            finish();
        }
        else {
            Toast.makeText(UpdateActivity.this, "Not allow empty title", Toast.LENGTH_SHORT).show();
        }
    }
    public void openLocation(View view) {
        // Get the string indicating a location. Input is not validated; it is
        // passed to the location handler intact.
        String loc = edtLocation.getText().toString();

        // Parse the location and create the intent.
        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        // Find an activity to handle the intent, and start that activity.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
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