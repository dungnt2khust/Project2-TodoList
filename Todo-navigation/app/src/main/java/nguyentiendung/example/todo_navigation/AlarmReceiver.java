package nguyentiendung.example.todo_navigation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarming...", Toast.LENGTH_SHORT).show();
        String ALARM_STATUS = intent.getExtras().getString("ALARM");
        Log.d("DDUNG", "onReceive: " + ALARM_STATUS);
        Intent musicIntent = new Intent(context, Music.class);
        musicIntent.putExtra("ALARM", ALARM_STATUS);
        context.startService(musicIntent);
    }
}