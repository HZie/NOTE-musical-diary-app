package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

public class SettingsActivity extends AppCompatActivity {

    private ImageView main;
    private  ImageView calendar;
    private ImageView stars;
    private ImageView settings;
    private EditText namespace;
    private EditText about;
    private Button editButton;
    private ImageView profileImage;
    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        namespace=findViewById(R.id.namespace);
        about=findViewById(R.id.infospace);
        editButton=findViewById(R.id.editButton);
        profileImage=findViewById(R.id.profileImage);

        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (editButton.getText().toString()=="EDIT"){
                    namespace.setEnabled(true);
                    about.setEnabled(true);
                    UserInfo.userName=namespace.getText().toString();
                    editButton.setText("SAVE");

                }else{
                    namespace.setEnabled(false);
                    about.setEnabled(false);
                    editButton.setText("EDIT");
                }

            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editButton.getText().toString()!="EDIT"){ // edit mode
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, REQUEST_CODE);

                }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    profileImage.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }


}
