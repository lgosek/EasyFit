package com.example.easyfit.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.easyfit.receivers.AlarmsBoradcastReceiver;

import java.sql.Time;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static android.content.Context.ALARM_SERVICE;

public class NotificationManager {
    private LinkedList<Time> notificationTimes;

    private static NotificationManager INSTANCE = null;



    public static NotificationManager getInstance(){
        if (INSTANCE == null ) {
            INSTANCE = new NotificationManager();
        }
        return INSTANCE;
    }

    private NotificationManager(){
        this.notificationTimes = new LinkedList<>();
//        this.notificationTimes.add(Time.valueOf("12:24:00"));
//        this.notificationTimes.add(Time.valueOf("13:56:00"));
//        this.notificationTimes.add(Time.valueOf("18:12:00"));
//        this.notificationTimes.add(Time.valueOf("19:24:00"));

    }

    public boolean add(Time t){
        if(!this.notificationTimes.contains(t)) {
            this.notificationTimes.add(t);
            Collections.sort(this.notificationTimes, new Comparator());
            return true;
        }
        return false;
    }

    public void edit(Time ol, Time ne){
        this.delete(ol);
        this.add(ne);
    }

    public void delete(Time t){
        this.notificationTimes.remove(t);
    }

    public Time get(int i){
        return this.notificationTimes.get(i);
    }

    public int getLength(){
        return this.notificationTimes.size();
    }

    public Set<String> getSet(){
        Set<String> result = new HashSet<>();
        for (Time t:this.notificationTimes) {
            result.add(t.toString());
        }
        return result;
    }

    private class Comparator implements java.util.Comparator<Time>{

        @Override
        public int compare(Time o1, Time o2) {
            if(o1.getTime() < o2.getTime())
                return -1;
            if(o1.getTime() > o2.getTime())
                return 1;
            return 0;
        }
    }

    public void setAlarm(Context context, String time, int hr, int min){
        AlarmManager alarmManager = (AlarmManager)(context.getSystemService(ALARM_SERVICE));

        Intent i = new Intent(context, AlarmsBoradcastReceiver.class);
        i.putExtra("notificationTime", time);
        i.putExtra("notificationHour", hr);
        i.putExtra("notificationMinutes", min);
        i.setAction("com.example.easyfit.NOTIFICATION");

        Calendar c = Calendar.getInstance();
        //c.add(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, hr);
        c.set(Calendar.MINUTE, min);
        long triggerTime = c.getTimeInMillis();

        PendingIntent pd = PendingIntent.getBroadcast(context, (int)Time.valueOf(time).getTime(), i, 0);
        //alarmManager.setRepeating(AlarmManager.RTC, triggerTime, AlarmManager.INTERVAL_DAY, pd);

        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC, triggerTime, pd);
        } else {
            alarmManager.setRepeating(AlarmManager.RTC, triggerTime, AlarmManager.INTERVAL_DAY, pd);
        }
    }
}
