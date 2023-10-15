package com.example.exp;

//import static com.example.exp.Manifest.*;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private EditText medicineNameEditText;
    private EditText timeEditText;
    private RadioGroup reminderOptionsRadioGroup;
    private Button setReminderButton;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    private static final int SMS_PERMISSION_REQUEST_CODE = 2;

    public MainActivity() {
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            // Check if the permission is granted or not
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                // You can perform the operations that require the SEND_SMS permission here
            } else {
                // Permission is denied
                // You can handle this case based on your requirements
            }
        }
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicineNameEditText = findViewById(R.id.medicineNameEditText);
        timeEditText = findViewById(R.id.timeEditText);
        reminderOptionsRadioGroup = findViewById(R.id.reminderOptionsRadioGroup);
        setReminderButton = findViewById(R.id.setReminderButton);

        setReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medicineName = medicineNameEditText.getText().toString();
                String time = timeEditText.getText().toString();
                String reminderOption = getSelectedReminderOption();

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    Log.i("enteee", "running");
                    // Permission is not granted, request it
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.SEND_SMS},
                            SMS_PERMISSION_REQUEST_CODE);
                } else {
                    SharedPreferenceHelper sph = new SharedPreferenceHelper(getApplicationContext());
                    ArrayList<String> Phno = sph.getContacts();
                    MessageSender ms = new MessageSender(Phno, "med time");
                }

              /*  if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    Log.i("enteee","running");
                    // Permission is not granted, request it
                    ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                            new String[]{Manifest.permission.SEND_SMS},
                            SMS_PERMISSION_REQUEST_CODE);

                } else {
                    SharedPreferenceHelper sph=new SharedP  referenceHelper(getApplicationContext());
                    ArrayList<String> Phno=sph.getContacts();
                    MessageSender ms=new MessageSender(Phno,"hello");
                }*/

//                SharedPreferenceHelper sph=new SharedPreferenceHelper(getApplicationContext());
//                ArrayList<String> Phno=sph.getContacts();
//                MessageSender ms=new MessageSender(Phno,"hello");
                // Validate inputs
                if (medicineName.isEmpty() || time.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter medicine name and time", Toast.LENGTH_SHORT).show();
                    return;
                }
                String[] timeParts = time.split(":");
                if (timeParts.length != 2) {
                    Toast.makeText(MainActivity.this, "Invalid time format", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Parse time input
                    int hour = Integer.parseInt(timeParts[0]);
                    int minute = Integer.parseInt(timeParts[1]);

                    // Create calendar instance with default time zone
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeZone(Calendar.getInstance().getTimeZone());
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);
                    // Set the alarm based on the selected reminder option
                    if (reminderOption.equals("Every Day")) {
                        // Set the alarm for every day
                        // ... (existing code)
                    } else if (reminderOption.equals("Sunday")) {
                        // Set the alarm for Sunday
                        // ... (existing code)
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    } else if (reminderOption.equals("Monday")) {
                        // Set the alarm for Monday
                        // ... (existing code)
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    } else if (reminderOption.equals("Tuesday")) {
                        // Set the alarm for Tuesday
                        // ... (existing code)
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                    } else if (reminderOption.equals("Wednesday")) {
                        // Set the alarm for Wednesday
                        // ... (existing code)
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                    } else if (reminderOption.equals("Thursday")) {
                        // Set the alarm for Thursday
                        // ... (existing code)
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                    } else if (reminderOption.equals("Friday")) {
                        // Set the alarm for Friday
                        // ... (existing code)
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                    } else if (reminderOption.equals("Saturday")) {
                        // Set the alarm for Saturday
                        // ... (existing code)
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid reminder option", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    // Create intent for alarm receiver
                    Intent intent = new Intent(MainActivity.this, AlarmreceiverActivity.class);
                    intent.putExtra("medicineName", medicineName);
                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_MUTABLE);

                    // Set the alarm
                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    } else {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    }

                    Toast.makeText(MainActivity.this, "Reminder set for " + time, Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid time format", Toast.LENGTH_SHORT).show();
                    return;
                }

// Start the AlarmreceiverActivity
                Intent intent = new Intent(MainActivity.this, AlarmreceiverActivity.class);
                intent.putExtra("medicineName", medicineName);
                startActivity(intent);
                // Start the ReminderDetailsActivity to display the medicine name and time immediately
                Intent detailsIntent = new Intent(MainActivity.this, ReminderdetailsactivityActivity.class);
                detailsIntent.putExtra("medicineName", medicineName);
                detailsIntent.putExtra("time", time);
                startActivity(detailsIntent);
            }

        });
    }


    private String getSelectedReminderOption() {
        int selectedRadioButtonId = reminderOptionsRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        if (selectedRadioButton != null) {
            return selectedRadioButton.getText().toString();
        }
        return "";
    }

}
