package nguyentiendung.example.todo_navigation;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class Music extends Service {
    MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String ALARM_STATUS = intent.getExtras().getString("ALARM");
        mediaPlayer = MediaPlayer.create(this, R.raw.alarmiphone);
        mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }
}
