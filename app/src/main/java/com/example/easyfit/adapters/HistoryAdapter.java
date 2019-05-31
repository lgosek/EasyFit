package com.example.easyfit.adapters;

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

import com.example.easyfit.R;
import com.example.easyfit.notifications.NotificationManager;
import com.example.easyfit.receivers.AlarmsBoradcastReceiver;

import java.sql.Time;

import static android.content.Context.ALARM_SERVICE;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    private Context context;
    String[] dates, calories, grams;

    public HistoryAdapter(Context ct){
        this.context = ct;

        dates = context.getApplicationContext().getResources().getStringArray(R.array.dates);
        calories = context.getApplicationContext().getResources().getStringArray(R.array.calories);
        grams = context.getApplicationContext().getResources().getStringArray(R.array.grams2);
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_entry, viewGroup, false);



        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder historyHolder, int i) {
        historyHolder.date.setText(dates[i]);
        historyHolder.calories.setText(calories[i]);
        historyHolder.carbs.setText(grams[i]);
        historyHolder.prots.setText(grams[i]);
        historyHolder.fat.setText(grams[i]);
    }

    @Override
    public int getItemCount() {
        if(dates!=null)
            return this.dates.length;
        else
            return 0;
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {

        TextView date, calories, carbs, prots, fat;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.historyDate);
            calories = itemView.findViewById(R.id.historyEntryEatenCaloriesText);
            carbs = itemView.findViewById(R.id.historyEntryEatenCarbsText);
            prots = itemView.findViewById(R.id.historyEntryEatenProteinsText);
            fat = itemView.findViewById(R.id.historyEntryEatenFatText);

        }


    }
}
