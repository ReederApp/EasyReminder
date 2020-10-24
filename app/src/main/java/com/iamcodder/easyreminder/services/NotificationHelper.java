package com.iamcodder.easyreminder.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.iamcodder.easyreminder.R;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private NotificationManager mManager;
    private Context mContext;

    public NotificationHelper(Context base) {
        super(base);
        this.mContext = base.getApplicationContext();
    }

    public NotificationCompat.Builder notiffff(String notificationTitle, String notificationContent) {
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel mChannel = null;


        int importance = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_HIGH;

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, channelID);
        builder.setContentTitle(mContext.getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_baseline_add_alarm_24)
                .setContentTitle(notificationTitle)
                .setContentText(notificationContent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(channelID, mContext.getString(R.string.app_name), importance);
            // Configure the notification channel.
//            mChannel.setDescription("Description");
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        } else {
            builder.setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setColor(ContextCompat.getColor(mContext, R.color.teal_200))
                    .setVibrate(new long[]{100, 250})
                    .setLights(Color.YELLOW, 500, 5000)
                    .setAutoCancel(true);
        }
        return builder;
    }
}