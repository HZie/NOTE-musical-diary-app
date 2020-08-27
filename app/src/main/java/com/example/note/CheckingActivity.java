package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CheckingActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking);

        Intent intent=getIntent();
        String feeling=intent.getStringExtra("feeling");

        TextView textView=findViewById(R.id.textView3);
        ImageView imageView=findViewById(R.id.imageView2);
        Button noButton =findViewById(R.id.button2);
        Button yesButton=findViewById(R.id.button3);

        if (feeling.equals("angry")) {
            imageView.setImageResource(R.drawable.angry);
            textView.setText("Are You Ready to \n Write Your Musical Anger?");
        }

        /////
        ///감정별 if문 작성
        ///musical calmness, musical happiness, musical sadness 정도?
        /////
        switch(feeling.charAt(0)){
            case 'a':
            case 'n':
            case 'h':
            case 's':
            default:
                break;
        }


        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CheckingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CheckingActivity.this,RecordActivity.class);
                startActivity(intent);
            }
        });



    }
}
