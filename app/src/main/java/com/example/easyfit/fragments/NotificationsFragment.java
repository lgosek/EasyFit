package com.example.easyfit.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.easyfit.R;
import com.example.easyfit.adapters.NotificationAdapter;
import com.example.easyfit.notifications.NotificationManager;

import java.sql.Time;

public class NotificationsFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton addButton;

    View view;

    int hr, min;

    //NotificationManager notificationManager;

    NotificationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notifications,container,false);

        recyclerView = view.findViewById(R.id.notificationsRecyclerView);
        addButton = view.findViewById(R.id.notificationAddButton);

        addButtonOnClickHandler();

        //notificationManager = NotificationManager.getInstance();

        //adapter = new MealsAdapter(this.getContext(), meals, calories);

        adapter = new NotificationAdapter(this.getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }

    private void addButtonOnClickHandler() {
        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(view.getContext(), timePickerListener, hr, min, true);
                dialog.show();
            }
        });
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            hr = hourOfDay;
            min = minutes;
            String time = new StringBuilder().append(hr).append(":").append(min).append(":00").toString();
            NotificationManager.getInstance().add(Time.valueOf(time));
            adapter.notifyDataSetChanged();


            // setting alarm
            NotificationManager.getInstance().setAlarm(getActivity(), time, hr, min);
        }
    };
}
