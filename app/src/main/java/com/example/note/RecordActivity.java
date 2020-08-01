package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import midi.MidiFile;
import midi.MidiTrack;
import midi.event.meta.Tempo;
import midi.event.meta.TimeSignature;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        generateNotes(60,87);
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
            long tick = 10;
            long duration = 5000;
            noteTrack.insertNote(channel,i,velocity,tick,duration);
            ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();
            tracks.add(tempoTrack);
            tracks.add(noteTrack);

            MidiFile midiNote = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);

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
}
