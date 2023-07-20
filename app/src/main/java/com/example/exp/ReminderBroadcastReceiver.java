package com.example.exp;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ReminderBroadcastReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "ReminderChannel";
    private static final int REQUEST_CODE_SET_ALARM = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Check if the SET_ALARM permission is granted
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SET_ALARM) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, proceed with creating the notification
            createNotification(context);
        } else {
            // Permission is not granted, handle the scenario accordingly
            // You can display a message or take alternative action
        }
    }

    private void createNotification(Context context) {
        // Create a notification channel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Reminder Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.my_notification_icon)
                .setContentTitle("Reminder")
                .setContentText("This is your reminder.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Check if the app has the necessary permission to show notifications
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED) {
            SharedPreferenceHelper sph=new SharedPreferenceHelper(context);
            ArrayList<String> Phno=sph.getContacts();
            MessageSender ms=new MessageSender(Phno,"hello");

            // Show the notification

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        } else {
            // Permission is not granted, handle the scenario accordingly
            // You can display a message or take alternative action
            Toast.makeText(context, "Permission to show notifications is required.", Toast.LENGTH_SHORT).show();

        }
    }
}
