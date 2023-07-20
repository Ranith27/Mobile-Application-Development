package com.example.exp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AfterloginActivity extends AppCompatActivity {
    private ImageView gifImageView2;
    private ImageView imageView3;
    private ImageView imageView7;

    private FloatingActionButton button;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afterlogin);
        gifImageView2=(ImageView) findViewById(R.id.gifImageView2);
        gifImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Diet1Activity.class);
                startActivity(intent);
            }
        });
        imageView3=(ImageView) findViewById(R.id.gifImageView15);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        button = findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EmptyreminderActivity.class);
                intent.putExtra("mode", 2);
                startActivity(intent);
            }
        });
        imageView7=(ImageView) findViewById(R.id.gifImageView34);
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EmptyreminderActivity.class);
                startActivity(intent);
            }
        });

    }
}
