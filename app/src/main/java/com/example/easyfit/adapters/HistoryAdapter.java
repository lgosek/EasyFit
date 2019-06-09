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
import com.example.easyfit.apiConnector.HistoryItem;
import com.example.easyfit.notifications.NotificationManager;
import com.example.easyfit.receivers.AlarmsBoradcastReceiver;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    private Context context;
    List<HistoryItem> historyItems;

    public HistoryAdapter(Context ct){
        this.historyItems = new ArrayList<>();
        this.context = ct;
    }

    public List<HistoryItem> getHistoryItems() {
        return historyItems;
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
        historyHolder.date.setText(historyItems.get(i).getDate());
        historyHolder.calories.setText(""+((int)Math.round(historyItems.get(i).getKcal())));
        historyHolder.carbs.setText(""+((int)Math.round(historyItems.get(i).getCarbohydrates())));
        historyHolder.prots.setText(""+((int)Math.round(historyItems.get(i).getProteins())));
        historyHolder.fat.setText(""+((int)Math.round(historyItems.get(i).getFats())));
    }

    @Override
    public int getItemCount() {
        return this.historyItems.size();
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
