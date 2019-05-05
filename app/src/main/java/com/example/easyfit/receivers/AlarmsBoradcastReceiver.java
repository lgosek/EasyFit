package com.example.easyfit.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.easyfit.R;

public class AlarmsBoradcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
        notification.setContentTitle("Przypomnienie o posiłku!");
        //notification.setContentText("You need to stand up");
        notification.setSmallIcon(R.mipmap.ic_launcher);


        notification.setDefaults(NotificationCompat.DEFAULT_ALL);
        notification.setAutoCancel(true);
        manager.notify(1, notification.build());
    }
}
