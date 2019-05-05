package com.example.easyfit.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.easyfit.notifications.NotificationManager;

import java.sql.Time;
import java.util.Calendar;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class SystemRebootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            SharedPreferences sh = context.getSharedPreferences("com.example.easyfit.sharedpreferences", MODE_PRIVATE);
            Set<String> set = sh.getStringSet("notificationTimes", null);

            sh.edit().putBoolean("edited", true).apply();

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Intent i = new Intent();
            i.setAction("com.example.easyfit.NOTIFICATION");

            PendingIntent pd;
            if(set != null){
                for (String s:set) {
                    Time t = Time.valueOf(s);
                    int hr = t.getHours();
                    int min = t.getMinutes();

                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.HOUR_OF_DAY, hr);
                    c.set(Calendar.MINUTE, min);
                    long triggerTime = c.getTimeInMillis();

                    pd = PendingIntent.getBroadcast(context, (int)Time.valueOf(s).getTime(), i, 0);
                    alarmManager.setRepeating(AlarmManager.RTC, triggerTime, AlarmManager.INTERVAL_DAY, pd);

                    Toast.makeText(context, s,Toast.LENGTH_SHORT).show();
                }
            }









        }
    }
}
