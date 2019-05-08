package com.example.easyfit.receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.easyfit.R;

public class AlarmsBoradcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
//
//        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
//        notification.setContentTitle("Przypomnienie o posiłku!");
//        //notification.setContentText("You need to stand up");
//        notification.setSmallIcon(R.mipmap.ic_launcher);
//
//
//        notification.setDefaults(NotificationCompat.DEFAULT_ALL);
//        notification.setAutoCancel(true);
//        manager.notify(1, notification.build());
        if (Build.VERSION.SDK_INT >= 23) {
            String time = intent.getExtras().getString("notificationTime", null);
            if(time!=null){
                int hr = intent.getExtras().getInt("notificationHour", 0);
                int min = intent.getExtras().getInt("notificationMinutes", 0);
                com.example.easyfit.notifications.NotificationManager.getInstance().setAlarm(context, time, hr, min);
            }
        }

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "com.example.easyfit.NOTIFICATIONCHANNEL";
        String channelName = "Powiadomienia o posiłkach";

        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Przypomnienie o posiłku!");

//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addNextIntent(intent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
//                0,
//                PendingIntent.FLAG_UPDATE_CURRENT
//        );
//        mBuilder.setContentIntent(resultPendingIntent);

        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        mBuilder.setAutoCancel(true);

        notificationManager.notify(notificationId, mBuilder.build());


    }
}
