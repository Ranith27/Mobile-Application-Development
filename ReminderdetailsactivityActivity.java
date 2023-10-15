package com.example.exp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ReminderdetailsactivityActivity extends AppCompatActivity {
    private TextView medicineNameTextView;
    private TextView timeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminderdetailsactivity);
        String medicineName = getIntent().getStringExtra("medicineName");


        medicineNameTextView = findViewById(R.id.medicineNameTextView);
        timeTextView = findViewById(R.id.timeTextView);
        Intent intent = getIntent();
        if (intent != null) {
            String medicineName1 = intent.getStringExtra("medicineName");
            String time = intent.getStringExtra("time");

            if (medicineName != null) {
                medicineNameTextView.setText(medicineName);
            }

            if (time != null) {
                timeTextView.setText(time);
            }
        }
    }

    }


