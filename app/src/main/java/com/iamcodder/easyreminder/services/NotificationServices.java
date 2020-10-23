package com.iamcodder.easyreminder.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.iamcodder.easyreminder.data.local.model.InfoModel;
import com.iamcodder.easyreminder.ui.MainActivity;

public class NotificationServices extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        InfoModel infoModel = intent.getParcelableExtra("infoModel");
        notify(getApplicationContext(), infoModel);
        return START_NOT_STICKY;
    }

    private void notify(Context context, InfoModel infoModel) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.notiffff(infoModel.getNotificationTitle(), infoModel.getNotificationContent());

        PendingIntent pendingIntent = notificationClick(infoModel);
        nb.setContentIntent(pendingIntent);

        Notification notification = nb.build();
        startForeground(infoModel.getIntentNumber(), notification);
        stopForeground(false);

    }

    private PendingIntent notificationClick(InfoModel infoModel) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.putExtra("infoModel", infoModel);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        return notifyPendingIntent;
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
