package com.iamcodder.easyreminder.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iamcodder.easyreminder.data.local.model.InfoModel;
import com.iamcodder.easyreminder.data.local.sharedPref.SharedPrefHelper;
import com.iamcodder.easyreminder.databinding.ActivityMainBinding;
import com.iamcodder.easyreminder.interfaces.SendData;
import com.iamcodder.easyreminder.services.AlertReceiver;
import com.iamcodder.easyreminder.ui.adapter.AlarmsAdapter;
import com.iamcodder.easyreminder.ui.fragment.EnteringDataFragment;
import com.iamcodder.easyreminder.ui.fragment.InfoFragment;
import com.iamcodder.easyreminder.ui.fragment.TimePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, SendData {
    private ActivityMainBinding binding;
    private SharedPrefHelper sharedPrefHelper;
    private Calendar tempCalendar;
    private AlarmsAdapter alarmsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View mView = binding.getRoot();
        setContentView(mView);
        sharedPrefHelper = new SharedPrefHelper(this.getApplicationContext());

        Intent intent = getIntent();
        InfoModel infoModel = intent.getParcelableExtra("infoModel");
        if (infoModel != null) {
            DialogFragment infoFragment = new InfoFragment(infoModel);
            infoFragment.show(getSupportFragmentManager(), "Info Fragment");

        }
        setRecyclerData(getSharedData());
        binding.fab.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "TimePickerFragment");
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
        int intentNumber = setAlarm(title, content);
        setSharedData(intentNumber, title, content);
        updateRecycler(intentNumber, title, content);
    }

    private void updateRecycler(int intentNumber, String title, String content) {
        if (alarmsAdapter != null) {
            InfoModel infoModel = new InfoModel(title, content, tempCalendar.get(Calendar.MINUTE), tempCalendar.get(Calendar.HOUR_OF_DAY), intentNumber);
            alarmsAdapter.updateUi(infoModel);

            binding.recyclerView.scrollToPosition(alarmsAdapter.getItemCount() - 1);
        } else {
            setRecyclerData(getSharedData());
        }


    }

    private void setRecyclerData(ArrayList list) {
        if (list.size() > 0) {
            ArrayList<InfoModel> tempList = (ArrayList<InfoModel>) list;
            alarmsAdapter = new AlarmsAdapter(tempList);
            binding.recyclerView.setAdapter(alarmsAdapter);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext(), RecyclerView.VERTICAL, false));
        }
    }

    private ArrayList getSharedData() {
        String alarms = (String) sharedPrefHelper.getValue("alarms", String.class);
        String[] alarmsArray = alarms.split(",");
        if (alarmsArray[0].equals("")) {
            Toast.makeText(this.getApplicationContext(), "Alarm yok", Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        } else {
            ArrayList<InfoModel> modelList = new ArrayList<>();
            for (String s : alarmsArray) {
                String title = (String) sharedPrefHelper.getValue(s + "title", String.class);
                String content = (String) sharedPrefHelper.getValue(s + "content", String.class);
                int hours = (int) sharedPrefHelper.getValue(s + "hours", Integer.class);
                int minutes = (int) sharedPrefHelper.getValue(s + "minutes", Integer.class);
                InfoModel tempModel = new InfoModel(title, content, minutes, hours, Integer.parseInt(s));
                modelList.add(tempModel);
            }
            return modelList;
        }
    }

    private void setSharedData(int intentNumber, String title, String content) {
        String alarms = (String) sharedPrefHelper.getValue("alarms", String.class);
        if (alarms != null && !alarms.isEmpty()) {
            alarms = alarms + "," + intentNumber;
        } else alarms = intentNumber + "";
        sharedPrefHelper.setValue("alarms", alarms);
        sharedPrefHelper.setValue(intentNumber + "title", title);
        sharedPrefHelper.setValue(intentNumber + "content", content);
        sharedPrefHelper.setValue(intentNumber + "hours", tempCalendar.get(Calendar.HOUR_OF_DAY));
        sharedPrefHelper.setValue(intentNumber + "minutes", tempCalendar.get(Calendar.MINUTE));
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

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }


}