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

import com.iamcodder.easyreminder.data.local.model.InfoModel;
import com.iamcodder.easyreminder.data.local.sharedPref.SharedPrefHelper;
import com.iamcodder.easyreminder.databinding.ActivityMainBinding;
import com.iamcodder.easyreminder.interfaces.SendData;
import com.iamcodder.easyreminder.services.AlertReceiver;
import com.iamcodder.easyreminder.ui.fragment.EnteringDataFragment;
import com.iamcodder.easyreminder.ui.fragment.InfoFragment;
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

        Intent intent = getIntent();
        InfoModel infoModel = intent.getParcelableExtra("infoModel");
        if (infoModel != null) {
            DialogFragment infoFragment = new InfoFragment(infoModel);
            infoFragment.show(getSupportFragmentManager(), "Info Fragment");

        }

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
    public void sendText(String title, String content) {
        setAlarm(title, content);
    }

    private void setAlarm(String title, String content) {
        if (title != null && content != null && !title.isEmpty() && !content.isEmpty()) {
            startAlarm(tempCalendar, title, content);
        }
    }

    private void startAlarm(Calendar c, String title, String content) {
        int intentNumber = (int) (Math.random() * 1000);

        Intent intent = new Intent(this, AlertReceiver.class);

        intent.putExtra("intentNumber", intentNumber);
        intent.putExtra("notificationTitle", title);
        intent.putExtra("notificationContent", content);
        intent.putExtra("calendarHours", c.get(Calendar.HOUR_OF_DAY));
        intent.putExtra("calendarMinute", c.get(Calendar.MINUTE));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), intentNumber, intent, 0);
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