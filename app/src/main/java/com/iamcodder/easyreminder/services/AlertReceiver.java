package com.iamcodder.easyreminder.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.iamcodder.easyreminder.data.local.model.InfoModel;


public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int intentNumber = intent.getIntExtra("intentNumber", 0);
        int calendarHours = intent.getIntExtra("calendarHours", 0);
        int calendarMinute = intent.getIntExtra("calendarMinute", 0);
        String notificationTitle = intent.getStringExtra("notificationTitle");
        String notificationContent = intent.getStringExtra("notificationContent");
        InfoModel infoModel = new InfoModel(notificationTitle, notificationContent, calendarMinute, calendarHours, intentNumber);

        Intent newIntent = new Intent(context.getApplicationContext(), NotificationServices.class);
        newIntent.putExtra("infoModel", infoModel);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(newIntent);
        } else {
            context.startService(newIntent);
        }
    }


}
