package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RecordActivity extends AppCompatActivity {
    EditText mMemoEdit = null;
    RecordManager mTextFileManager = new RecordManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        mMemoEdit = (EditText) findViewById(R.id.diaryedit);
    }

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.saveButton:{
                String memoData = mMemoEdit.getText().toString();
                mTextFileManager.save(memoData);
                mMemoEdit.setText("");
                Toast.makeText(this, "저장 완료", Toast.LENGTH_LONG).show();
                break;

            }
            case R.id.dateButton:{
            }
        }
    }
}
