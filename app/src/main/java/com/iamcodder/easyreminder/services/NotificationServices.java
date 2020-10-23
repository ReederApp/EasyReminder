package com.iamcodder.easyreminder.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class NotificationServices extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int intentNumber = intent.getIntExtra("intentNumber", 0);
        String notificationText = intent.getStringExtra("notificationText");
        notify(getApplicationContext(), 1, notificationText);
        return START_STICKY;
    }

    private void notify(Context context, int number, String notificationText) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(notificationText);
        notificationHelper.getManager().notify(1, nb.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
