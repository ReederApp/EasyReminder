package com.iamcodder.easyreminder.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int intentNumber = intent.getIntExtra("intentNumber", 0);
        String notificationText = intent.getStringExtra("notificationText");

        Intent newIntent = new Intent(context.getApplicationContext(), NotificationServices.class);
        newIntent.putExtra("notificationText", notificationText);
        newIntent.putExtra("intentNumber", intentNumber);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(newIntent);
        } else {
            context.startService(newIntent);
        }

    }


}
