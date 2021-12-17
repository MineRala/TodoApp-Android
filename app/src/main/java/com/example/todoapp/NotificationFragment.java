package com.example.todoapp;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class NotificationFragment extends Fragment implements View.OnClickListener{
    public int notificationId = 1;
    TextView cancelButton, saveButton;
    EditText name;
    TimePicker timePicker;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_notification, container, false);
        NotificationActivity.getInstance().button.setVisibility(View.GONE);
        saveButton = view.findViewById(R.id.saveButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        name = view.findViewById(R.id.editName);
        timePicker = view.findViewById(R.id.timePicker);
        cancelButton.setOnClickListener((View.OnClickListener) this);
        saveButton.setOnClickListener((View.OnClickListener) this);
        return view;
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("message",name.getText().toString());

        PendingIntent alarmIntent = PendingIntent.getBroadcast(
                getActivity(),0,intent,PendingIntent.FLAG_CANCEL_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getContext().ALARM_SERVICE);

        switch (v.getId()) {
            case R.id.saveButton:
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY,hour);
                startTime.set(Calendar.MINUTE,minute);
                startTime.set(Calendar.SECOND,0);
                long alarmStartTime = startTime.getTimeInMillis();
                alarmManager.set(AlarmManager.RTC_WAKEUP,alarmStartTime,alarmIntent);
                Toast.makeText(getActivity(), "Done!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cancelButton:
                alarmManager.cancel(alarmIntent);
                break;
        }

    }

}