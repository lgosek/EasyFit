package com.example.easyfit;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.easyfit.notifications.NotificationManager;

import java.sql.Time;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {

    private Context context;
    //NotificationManager notificationManager;

    public NotificationAdapter(Context ct){
        this.context = ct;
        //this.notificationManager = mn;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notification, viewGroup, false);
        return new NotificationHolder(view, i);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder notificationHolder, int i) {
        Time t = NotificationManager.getInstance().get(i);
        String time = t.toString();
        time = time.substring(0, time.length()-3);
        notificationHolder.time.setText(time);
    }

    @Override
    public int getItemCount() {
        return NotificationManager.getInstance().getLength();
    }

    public class NotificationHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView time;
        int position, hr, min;

        public NotificationHolder(@NonNull View itemView, int i) {
            super(itemView);
            time = itemView.findViewById(R.id.timeText);
            position = i;

            time.setOnCreateContextMenuListener(this);

//            ((Activity)context).registerForContextMenu(itemView);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            ((Activity)context).getMenuInflater().inflate(R.menu.notification_context_menu,menu);

            menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    //Toast.makeText(context, "Edited", Toast.LENGTH_SHORT).show();

                    TimePickerDialog dialog = new TimePickerDialog(context, timePickerListener, hr, min, true);
                    dialog.show();


                    return true;
                }
            });

            menu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    //Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                    Time t = Time.valueOf(time.getText().toString()+":00");
                    long tt = t.getTime();


                    AlarmManager alarmManager = (AlarmManager)(context.getSystemService(ALARM_SERVICE));

                    Intent i1 = new Intent();
                    i1.setAction("com.example.easyfit.NOTIFICATION");

                    PendingIntent pd = PendingIntent.getBroadcast(context, (int)tt, i1, 0);
                    alarmManager.cancel(pd);

                    NotificationManager.getInstance().delete(t);
                    NotificationAdapter.super.notifyDataSetChanged();
                    return true;
                }
            });

        }

        private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                hr = hourOfDay;
                min = minutes;
                String time = new StringBuilder().append(hr).append(":").append(min).append(":00").toString();

                Time t = Time.valueOf(NotificationHolder.this.time.getText().toString()+":00");
                long tt = t.getTime();

                // deleting alarm
                AlarmManager alarmManager = (AlarmManager)(context.getSystemService(ALARM_SERVICE));

                Intent i1 = new Intent();
                i1.setAction("com.example.easyfit.NOTIFICATION");

                PendingIntent pd = PendingIntent.getBroadcast(context, (int)tt, i1, 0);
                alarmManager.cancel(pd);

                //setting up a new alarm

                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hr);
                c.set(Calendar.MINUTE, min);
                long triggerTime = c.getTimeInMillis();

                pd = PendingIntent.getBroadcast(context, (int)Time.valueOf(time).getTime(), i1, 0);
                alarmManager.setRepeating(AlarmManager.RTC, triggerTime, AlarmManager.INTERVAL_DAY, pd);

                // rest
                NotificationManager.getInstance().edit(t, Time.valueOf(time));
                NotificationAdapter.super.notifyDataSetChanged();






            }
        };
    }
}
