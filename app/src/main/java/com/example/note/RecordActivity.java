package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

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

    RecordManager mTextFileManager = new RecordManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mMemoEdit = findViewById(R.id.diaryedit);
        dateText=findViewById(R.id.dateText);
        saveButton=findViewById(R.id.saveButton);

        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd");
        Date date=new Date();
        String dateFormat=format.format(date);
        dateText.setText(dateFormat);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memoData = mMemoEdit.getText().toString();
                mTextFileManager.save(memoData);
                mMemoEdit.setText("");
                Toast.makeText(getApplicationContext(), "save completed", Toast.LENGTH_LONG).show();
            }
        });

    }

}
