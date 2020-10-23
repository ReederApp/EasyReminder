package com.iamcodder.easyreminder.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;


public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int randomNumber = intent.getIntExtra("randomNumber", 0);
        notify(context, randomNumber);

    }

    private void notify(Context context, int number) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification("" + number);
        notificationHelper.getManager().notify(1, nb.build());
    }


}
