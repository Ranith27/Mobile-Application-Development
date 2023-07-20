package com.example.exp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AlarmreceiverActivity extends BroadcastReceiver {

        @SuppressLint("MissingPermission")
        public void onReceive(Context context, Intent intent) {
            String medicineName = intent.getStringExtra("medicineName");
            String alarmTime = intent.getStringExtra("alarmTime");
            Log.d("AlarmReceiver", "Received alarm for medicine: " + medicineName);
            // Create a notification channel (required for Android 8.0 and above)
            createNotificationChannel(context);

            // Build the notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                    .setSmallIcon(R.drawable.my_notification_icon)
                    .setContentTitle("Medicine Reminder")
                    .setContentText("Time to take " + medicineName)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            // Display the notification
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(1, builder.build());
            // Start the ReminderDetailsActivity and pass the medicine name and alarm time
            Intent detailsIntent = new Intent(context, ReminderdetailsactivityActivity.class);
            detailsIntent.putExtra("medicineName", medicineName);
            detailsIntent.putExtra("alarmTime", alarmTime);
            detailsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(detailsIntent);

        }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
