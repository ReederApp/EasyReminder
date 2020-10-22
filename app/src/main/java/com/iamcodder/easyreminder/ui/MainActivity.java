package com.iamcodder.easyreminder.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.iamcodder.easyreminder.data.local.sharedPref.SharedPrefHelper;
import com.iamcodder.easyreminder.databinding.ActivityMainBinding;
import com.iamcodder.easyreminder.interfaces.SendData;
import com.iamcodder.easyreminder.services.AlertReceiver;
import com.iamcodder.easyreminder.ui.fragment.EnteringDataFragment;
import com.iamcodder.easyreminder.ui.fragment.TimePickerFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, SendData {
    private ActivityMainBinding binding;
    private SharedPrefHelper sharedPrefHelper;

    private Calendar tempCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View mView = binding.getRoot();
        setContentView(mView);
        sharedPrefHelper = new SharedPrefHelper(this.getApplicationContext());

        binding.fab.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "TimePickerFragment");
        });

        binding.cancelButton.setOnClickListener(v -> {
            cancelAlarm();
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        tempCalendar = Calendar.getInstance();
        tempCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        tempCalendar.set(Calendar.MINUTE, minute);
        tempCalendar.set(Calendar.SECOND, 0);
        DialogFragment dataFragment = new EnteringDataFragment(this);
        dataFragment.show(getSupportFragmentManager(), "DataFragment");
    }

    @Override
    public void sendText(String text) {
        setAlarm(text);
    }

    private void setAlarm(String text) {
        if (text != null) {
            startAlarm(tempCalendar, text);
        }
    }


    private void startAlarm(Calendar c, String notificationText) {
        int randomNumber = (int) (Math.random() * 1000);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("randomNumber", randomNumber);
        intent.putExtra("notificationText", notificationText);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), randomNumber, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        binding.text.setText("Alarm canceled");
    }


}