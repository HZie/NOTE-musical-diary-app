package com.example.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmObject;
import midi.MidiFile;
import midi.MidiTrack;
import midi.event.meta.Tempo;
import midi.event.meta.TimeSignature;


public class RecordActivity extends AppCompatActivity {
    private static HashMap<Integer, String> noteName = new HashMap<Integer, String>();
    String noteString[] = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
    private static TextView noteView;
    private static ArrayList<String> melodyStr = new ArrayList<String>();
    private static ArrayList<Integer> melody = new ArrayList<Integer>();
    private Button btnSave;
    private Button btnListen;
    private Button btnClear;
    private static boolean isClean = true;

    private MediaPlayer mp = null;
    String fileName;

    EditText mMemoEdit;
    RecordManager mTextFileManager;
    ConstraintLayout modal;
    TextView modalText;
    Button leftbtn;
    Button rightbtn;

    String checkedFeeling;
    String todayDate;

    TextView tvDate;

    Realm realm;
    DiaryMetadata metadata;

    @Override
    public void onBackPressed() {

        modal=findViewById(R.id.modal);
        modalText=findViewById(R.id.modalText);
        leftbtn=findViewById(R.id.leftbtn);
        rightbtn=findViewById(R.id.rightbtn);

        modal.setVisibility(View.VISIBLE);
        modalText.setText("Work will be not saved");
        rightbtn.setText("Continue");
        leftbtn.setText("Cancel");

        rightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modal.setVisibility(View.GONE);
                Intent intent=new Intent(RecordActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        leftbtn.setOnClickListener(new View.OnClickListener() {
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
        generateNotes(60,87);
        loadNotes(60,87);

        mTextFileManager = new RecordManager(this);
        mMemoEdit = findViewById(R.id.diaryedit);

        Intent intent = getIntent();
        checkedFeeling = intent.getStringExtra("feeling");
        todayDate = intent.getStringExtra("todayDate");

        fileName = todayDate+"_"+checkedFeeling;

        noteView=(TextView)findViewById(R.id.noteText);
        btnSave = (Button)findViewById(R.id.btn_recordSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveMemo();
                createMelody();
                Intent intent=new Intent(getApplicationContext(),CalendarActivity.class);
                startActivity(intent);
                Toast toast = Toast.makeText(getApplicationContext(),"Your melody and memo are saved",Toast.LENGTH_LONG);
                melody.clear();
                melodyStr.clear();
                isClean = true;
                toast.show();
            }
        });

        btnClear = (Button)findViewById(R.id.btn_recordClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                melodyStr.clear();
                melody.clear();
                noteView.setText("");
                isClean = true;
                Toast toast = Toast.makeText(getApplicationContext(),"Your melody is cleared",Toast.LENGTH_LONG);
                toast.show();
            }
        });

        btnListen = (Button)findViewById(R.id.btn_recordListen);
        btnListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                melodyPlay();
            }
        });

        tvDate = findViewById(R.id.todayDate);
        tvDate.setText(todayDate);



/*
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd");
        Date date=new Date();
        String dateFormat=format.format(date);

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
*/

    }



    public static void addMelody(int key){
        if(isClean){
            noteView.setText("");
            isClean = false;
        }
        melody.add(key);
        melodyStr.add(noteName.get(key));
        noteView.setText(noteView.getText()+" " +noteName.get(key));
    }

    // Generate notes
    private void generateNotes(int start, int last){
        File folderMidiNotes = new File(this.getApplicationContext().getFilesDir(), "MidiNotes");
        if(!folderMidiNotes.exists()){
            folderMidiNotes.mkdir();
        }

        for(int i = start; i <= last; i++){
            MidiTrack tempoTrack = new MidiTrack();
            MidiTrack noteTrack = new MidiTrack();

            TimeSignature ts = new TimeSignature();
            ts.setTimeSignature(4,4,TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);

            Tempo tempo = new Tempo();
            tempo.setBpm(180);

            tempoTrack.insertEvent(ts);
            tempoTrack.insertEvent(tempo);

            // Track 1 will have notes
            int channel = 0;
            int velocity = 100;
            long tick = 1;
            long duration = 5000;
            noteTrack.insertNote(channel,i,velocity,tick,duration);
            ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();
            tracks.add(tempoTrack);
            tracks.add(noteTrack);

            MidiFile midiNote = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
            int num = (i-60)/noteString.length+ 3;
            String note = noteString[(i-60) % noteString.length] + num;
            noteName.put(i, note); // 계이름 저장하기

            // Create midi files for notes
            String dirPath = this.getApplicationContext().getFilesDir().toString();
            File noteFile = new File(dirPath+"/MidiNotes/",i+".mid");
            try{
                midiNote.writeToFile(noteFile);
            }catch(IOException e){
                System.err.println(e);
            }

        }
    }

    // load notes
    private static HashMap<Integer, Uri> noteMap = new HashMap<Integer, Uri>();

    private void loadNotes(int start, int end){
        for(int i = start; i <= end; i++){
            try{
                Uri uriPath = Uri.parse(getFilesDir()+"/MidiNotes/"+i+".mid");
                noteMap.put(i,uriPath);
            }
            catch(Exception e){
                System.err.print(e);
            }
        }
    }


    private void createMelody(){
        metadata = new DiaryMetadata(fileName);
        File folderMelody = new File(this.getApplicationContext().getFilesDir(), "Melodies");
        if(!folderMelody.exists()){
            folderMelody.mkdir();
        }
        MidiTrack tempoTrack = new MidiTrack();
        MidiTrack noteTrack = new MidiTrack();

        TimeSignature ts = new TimeSignature();
        ts.setTimeSignature(4,4,TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);

        Tempo tempo = new Tempo();
        tempo.setBpm(180);

        tempoTrack.insertEvent(ts);
        tempoTrack.insertEvent(tempo);
        // Track 1 will have notes
        int channel = 0;
        int velocity = 100;
        long tick;
        long duration;

        for(int i = 0; i < melody.size(); i++){
            tick = i*480;
            duration = 120+i*10;
            noteTrack.insertNote(channel,melody.get(i),velocity,tick,duration);
        }

        ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();
        tracks.add(tempoTrack);
        tracks.add(noteTrack);
        MidiFile midiMelody = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
        // Create midi files for notes
        String dirPath = this.getApplicationContext().getFilesDir().toString();
        File noteFile = new File(dirPath+"/Melodies/",fileName+".mid");
        try{
            midiMelody.writeToFile(noteFile);
        }catch(IOException e){
            System.err.println(e);
        }
        DBTransaction();
    }

    private void melodyPlay(){
        if(mp == null){
            createMelody();
            mp = MediaPlayer.create(this.getApplicationContext(), Uri.parse(getFilesDir()+"/Melodies/"+fileName+".mid"));
        }
        if(!mp.isPlaying()){
            mp.start();
        }
        else{
            mp.pause();
            mp.release();
            mp = null;
        }

    }

    public static HashMap<Integer, Uri> getNoteMap(){
        return noteMap;
    }
    public static HashMap<Integer, String> getNoteName(){
        return noteName;
    }

    private void saveMemo(){
        File memoFolder = new File(this.getApplicationContext().getFilesDir(),"Memos");
        if(!memoFolder.exists()){
            memoFolder.mkdir();
        }

        String dirPath = this.getApplicationContext().getFilesDir().toString();
        File memoFile = new File(dirPath+"/Memos/",fileName+".txt");

        String memoData = mMemoEdit.getText().toString();

        try{
            BufferedWriter buff = new BufferedWriter(new FileWriter(memoFile));
            buff.write(memoData);
            buff.close();
        }catch(IOException e){
            System.err.println(e);
        }
    }

    void DBTransaction(){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(metadata);
        realm.commitTransaction();
        realm.close();
    }
}


