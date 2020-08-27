package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class FirstScreenActivity extends AppCompatActivity {
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private Button button;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        textView=findViewById(R.id.textView);
        if (UserInfo.userName=="name"){
            textView.setText("How are you?");
        }else{
            textView.setText("How are you, "+UserInfo.userName+"?");
        }

        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstScreenActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });





    }
}
