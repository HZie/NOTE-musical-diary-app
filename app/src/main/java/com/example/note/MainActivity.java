package com.example.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity {

    private ImageView happy;
    private ImageView neutral;
    private ImageView sad;
    private  ImageView angry;
    private ImageView main;
    private  ImageView calendar;
    private ImageView stars;
    private ImageView settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        happy=findViewById(R.id.happy);
        neutral=findViewById(R.id.neutral);
        sad=findViewById(R.id.sad);
        angry=findViewById(R.id.angry);


        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        neutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CheckingActivity.class);
                intent.putExtra("feeling","angry");
                startActivity(intent);
            }
        });

        main=findViewById(R.id.main);
        calendar=findViewById(R.id.calendar);
        stars=findViewById(R.id.stars);
        settings=findViewById(R.id.settings);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CalendarActivity.class);
                startActivity(intent);

            }
        });

        stars.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),StarsActivity.class);
                startActivity(intent);

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(intent);

            }
        });







    }



}
