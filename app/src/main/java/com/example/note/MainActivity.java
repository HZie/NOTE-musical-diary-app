package com.example.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

    private ConstraintLayout checkingModal;
    private TextView checkingModalText;
    private Button goBack;
    private Button continue_;
    private ConstraintLayout wholeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        happy=findViewById(R.id.happy);
        neutral=findViewById(R.id.neutral);
        sad=findViewById(R.id.sad);
        angry=findViewById(R.id.angry);

        checkingModal=findViewById(R.id.checkingModal);
        checkingModalText=findViewById(R.id.checkingModalText);
        goBack=findViewById(R.id.goBack);
        continue_=findViewById(R.id.continue_);

        wholeScreen=findViewById(R.id.wholeScreen);



        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkingModal.setVisibility(View.VISIBLE);
                checkingModalText.setText("write a happy music" );            }
        });

        neutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkingModal.setVisibility(View.VISIBLE);
                checkingModalText.setText("write a music" );
                // 여기 채우기
            }
        });
        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkingModal.setVisibility(View.VISIBLE);
                checkingModalText.setText("write a sad music" );
                // 여기 채우기
            }
        });

        angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkingModal.setVisibility(View.VISIBLE);
                checkingModalText.setText("write an angry music" );
            }
        });



        continue_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });

        goBack.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkingModal.setVisibility(View.GONE);
            }
        }));

        main=findViewById(R.id.main);
        calendar=findViewById(R.id.calendar);
        stars=findViewById(R.id.stars);
        settings=findViewById(R.id.settings);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0,0); //0 for no animation
                startActivity(intent);


            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),CalendarActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0,0); //0 for no animation
                startActivity(intent);


            }
        });

        stars.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),StarsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0,0); //0 for no animation
                startActivity(intent);


            }
        });

        settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SettingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0,0); //0 for no animation
                startActivity(intent);


            }
        });







    }



}
