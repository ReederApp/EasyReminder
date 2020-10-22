package com.iamcodder.easyreminder.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;


public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int randomNumber = intent.getIntExtra("randomNumber", 0);
        String notificationText = intent.getStringExtra("notificationText");
        notify(context, randomNumber, notificationText);

    }

    private void notify(Context context, int number, String notificationText) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(notificationText);
        notificationHelper.getManager().notify(1, nb.build());
    }


}
