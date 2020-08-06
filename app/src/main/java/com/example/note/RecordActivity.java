package com.example.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordActivity extends AppCompatActivity {



    TextView dateText;
    EditText mMemoEdit;
    Button saveButton;
    RecordManager mTextFileManager;
    ConstraintLayout modal;
    TextView modalText;
    Button leftbtn;
    Button rightbtn;

    @Override
    public void onBackPressed() {

        modal=findViewById(R.id.modal);
        modalText=findViewById(R.id.modalText);
        leftbtn=findViewById(R.id.leftbtn);
        rightbtn=findViewById(R.id.rightbtn);

        modal.setVisibility(View.VISIBLE);
        modalText.setText("Work will be not saved");
        leftbtn.setText("Continue");
        rightbtn.setText("Cancel");

        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modal.setVisibility(View.GONE);
                Intent intent=new Intent(RecordActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        rightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modal.setVisibility(View.GONE);

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mTextFileManager = new RecordManager(this);
        mMemoEdit = findViewById(R.id.diaryedit);
        dateText=findViewById(R.id.dateText);
        saveButton=findViewById(R.id.saveButton);
        modal=findViewById(R.id.modal);
        modalText=findViewById(R.id.modalText);
        leftbtn=findViewById(R.id.leftbtn);
        rightbtn=findViewById(R.id.rightbtn);


        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd");
        Date date=new Date();
        String dateFormat=format.format(date);
        dateText.setText(dateFormat);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modal.setVisibility(View.VISIBLE);
                modalText.setText("Are you sure to save?");
                leftbtn.setText("NO");
                rightbtn.setText("YES");

            }
        });

        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modal.setVisibility(View.GONE);
            }
        });

        rightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modal.setVisibility(View.GONE);
                String memoData = mMemoEdit.getText().toString();
                mTextFileManager.save(memoData);
                mMemoEdit.setText("");
                Toast.makeText(getApplicationContext(), "save completed", Toast.LENGTH_LONG).show();
            }
        });

    }

}
