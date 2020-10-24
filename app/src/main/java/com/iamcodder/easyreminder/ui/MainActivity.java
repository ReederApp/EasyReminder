package com.iamcodder.easyreminder.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iamcodder.easyreminder.data.local.model.InfoModel;
import com.iamcodder.easyreminder.data.local.repository.MainRepository;
import com.iamcodder.easyreminder.data.local.sharedPref.SharedPrefHelper;
import com.iamcodder.easyreminder.databinding.ActivityMainBinding;
import com.iamcodder.easyreminder.interfaces.SendData;
import com.iamcodder.easyreminder.services.AlertReceiver;
import com.iamcodder.easyreminder.ui.adapter.AlarmsAdapter;
import com.iamcodder.easyreminder.ui.fragment.DatePickerFragment;
import com.iamcodder.easyreminder.ui.fragment.EnteringDataFragment;
import com.iamcodder.easyreminder.ui.fragment.InfoFragment;
import com.iamcodder.easyreminder.ui.fragment.TimePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SendData {
    private ActivityMainBinding binding;
    private Calendar tempCalendar;
    private AlarmsAdapter alarmsAdapter;
    private MainRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View mView = binding.getRoot();
        setContentView(mView);
        SharedPrefHelper sharedPrefHelper = new SharedPrefHelper(this.getApplicationContext());
        repository = new MainRepository(sharedPrefHelper);

        Intent intent = getIntent();
        InfoModel infoModel = intent.getParcelableExtra("infoModel");
        if (infoModel != null) {
            DialogFragment infoFragment = new InfoFragment(infoModel);
            infoFragment.show(getSupportFragmentManager(), "Info Fragment");
            repository.deleteSharedData(infoModel);
        }
        setRecyclerData(repository.getSharedData(this.getApplicationContext()));
        binding.fab.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment(this);
            datePicker.show(getSupportFragmentManager(), "TimePickerFragment");
        });

    }

    @Override
    public void sendDialogData(String title, String content) {
        int intentNumber = setAlarm(title, content);
        repository.setSharedData(intentNumber, title, content, tempCalendar);
        addNewItemRecycler(intentNumber, title, content);
    }

    @Override
    public void sendDate(int year, int month, int day) {
        tempCalendar = Calendar.getInstance();
        int currentYear = tempCalendar.get(Calendar.YEAR);
        int currentMonth = tempCalendar.get(Calendar.MONTH) + 1;
        int currentDay = tempCalendar.get(Calendar.DAY_OF_MONTH);

        if (year >= currentYear && month >= currentMonth && day >= currentDay) {
            //time picker aç
            tempCalendar.set(Calendar.YEAR, year);
            tempCalendar.set(Calendar.MONTH, month - 1);
            tempCalendar.set(Calendar.DAY_OF_MONTH, day);
            DialogFragment timePicker = new TimePickerFragment(this);
            timePicker.show(getSupportFragmentManager(), "Time Picker");
        } else {
            Toast.makeText(this, "İleri bir tarih seçiniz", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void sendTime(int hour, int minute) {
        int currentHour = tempCalendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = tempCalendar.get(Calendar.MINUTE);
        if (hour >= currentHour && minute >= currentMinute) {
            tempCalendar.set(Calendar.HOUR_OF_DAY, currentHour);
            tempCalendar.set(Calendar.MINUTE, minute);
            tempCalendar.set(Calendar.SECOND, 0);
            DialogFragment dataFragment = new EnteringDataFragment(this);
            dataFragment.show(getSupportFragmentManager(), "DataFragment");
        } else {
            Toast.makeText(this, "Bugünün alarmı için " + currentHour + "." + currentMinute + " ile 23.59'aralığında saat seçilmelidir", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void sendAlarmInfo(int position, InfoModel infoModel) {
        deleteItemOnRecycler(position, infoModel);
    }

    private void deleteItemOnRecycler(int position, InfoModel infoModel) {
        alarmsAdapter.deleteItem(position);
        int intentNumber = infoModel.getIntentNumber();
        cancelAlarm(intentNumber);
        repository.deleteSharedData(infoModel);
    }

    private void addNewItemRecycler(int intentNumber, String title, String content) {
        if (alarmsAdapter != null) {
            InfoModel infoModel = new InfoModel(title, content, tempCalendar.get(Calendar.MINUTE), tempCalendar.get(Calendar.HOUR_OF_DAY), intentNumber);
            alarmsAdapter.addNewItem(infoModel);
            binding.recyclerView.scrollToPosition(alarmsAdapter.getItemCount() - 1);
        } else {
            setRecyclerData(repository.getSharedData(this.getApplicationContext()));
        }
    }

    private void setRecyclerData(ArrayList list) {
        if (list.size() > 0) {
            ArrayList<InfoModel> tempList = (ArrayList<InfoModel>) list;
            alarmsAdapter = new AlarmsAdapter(tempList, this);
            binding.recyclerView.setAdapter(alarmsAdapter);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext(), RecyclerView.VERTICAL, false));
        }
    }

    private int setAlarm(String title, String content) {
        if (title != null && content != null && !title.isEmpty() && !content.isEmpty()) {
            return startAlarm(tempCalendar, title, content);
        } else return 0;
    }

    private int startAlarm(Calendar c, String title, String content) {
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
        return intentNumber;
    }

    private void cancelAlarm(int intentNumber) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, intentNumber, intent, 0);
        alarmManager.cancel(pendingIntent);
    }


}