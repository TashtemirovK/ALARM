package com.example.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class AlarmService extends Service {

    public static final String ALARM_TIME_EXTRA = "ALARM_TIME_EXTRA";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra(ALARM_TIME_EXTRA)) {
            long alarmTime = intent.getLongExtra(ALARM_TIME_EXTRA, 0);
            setAlarm(alarmTime);
        }

        return START_NOT_STICKY;
    }

    private void setAlarm(long alarmTime) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent alarmActionIntent = new Intent(this, AlarmActivity.class);
        PendingIntent actionPendingIntent = PendingIntent.getActivity(this, 1, alarmActionIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Use setAlarmClock for Android version M and above
            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(alarmTime, actionPendingIntent);
            alarmManager.setAlarmClock(alarmClockInfo, actionPendingIntent);
        } else {
            // For older versions, use setExact
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, actionPendingIntent);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


