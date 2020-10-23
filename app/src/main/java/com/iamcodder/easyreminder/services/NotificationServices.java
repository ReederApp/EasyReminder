package com.iamcodder.easyreminder.services;

import android.app.Notification;
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
        String notificationTitle = intent.getStringExtra("notificationTitle");
        String notificationContent = intent.getStringExtra("notificationContent");
        notify(getApplicationContext(), intentNumber, notificationTitle, notificationContent);
        return START_NOT_STICKY;
    }

    private void notify(Context context, int intentNumber, String notificationTitle, String notificationContent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.notiffff(notificationTitle, notificationContent);
        Notification notification = nb.build();
        startForeground(intentNumber, notification);
        stopForeground(false);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
