package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;


import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class CalendarActivity extends AppCompatActivity
        implements OnDateSelectedListener, OnMonthChangedListener, OnDateLongClickListener {


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final CharSequence[] ITEMS =
            new CharSequence[] { "EDIT", "SINGLE", "MULTIPLE", "RANGE" };

    private ImageView main;
    private  ImageView calendar;
    private ImageView stars;
    private ImageView settings;

    // 파일 재생
    private MediaPlayer mp = null;
    private Realm realm;

    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

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


        CheckBox calendar_mode =findViewById(R.id.calendar_mode);
        calendar_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MaterialCalendarView widget = findViewById(R.id.calendarView);
                final CalendarMode mode = isChecked ? CalendarMode.WEEKS : CalendarMode.MONTHS;
                widget.state().edit().setCalendarDisplayMode(mode).commit();

            }
        });


        Button selection_mode = (Button)findViewById(R.id.button_selection_mode);
        selection_mode.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                MaterialCalendarView widget =findViewById(R.id.calendarView);

                new AlertDialog.Builder( v.getContext() )
                        .setTitle("Selection Mode")
                        .setSingleChoiceItems(ITEMS, widget.getSelectionMode(),new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MaterialCalendarView widget = findViewById(R.id.calendarView);

                                // do something here
                                widget.setSelectionMode(which);
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        final Button get_selected_dates = (Button)findViewById(R.id.get_selected_dates);
        get_selected_dates.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                MaterialCalendarView widget = (MaterialCalendarView)findViewById(R.id.calendarView);
                final List<CalendarDay> selectedDates = widget.getSelectedDates();

                if(mp == null || !mp.isPlaying()){
                    String msg = "\n";

                    if (!selectedDates.isEmpty()) {
                        for(int i = 0; i < selectedDates.size();){
                            if(mp == null || !mp.isPlaying() ){
                                msg +=getFileNameFromDB(FORMATTER.format(selectedDates.get(i).getDate())) + "\n";
                                i++;
                                Log.d("msg",msg);

                            }

                        }

                        toast = Toast.makeText(v.getContext(),msg,Toast.LENGTH_LONG);
                        toast.show();
                        Log.d("GettersActivity", selectedDates.toString());
                    } else {
                        toast = Toast.makeText(v.getContext(), "No Selection", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }



            }
        });

    }

    @Override
    public void onDateSelected(
            @NonNull MaterialCalendarView widget,
            @NonNull CalendarDay date,
            boolean selected) {
        //  MaterialCalendarView widget = (MaterialCalendarView)findViewById(R.id.calendarView);
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(selected ? FORMATTER.format(date.getDate()) : "No Selection");
    }

    @Override
    public void onDateLongClick(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {
        final String text = String.format("%s is available", FORMATTER.format(date.getDate()));
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
    }


    private void melodyPlay(String fileName){

        mp = MediaPlayer.create(this.getApplicationContext(), Uri.parse(getFilesDir()+"/Melodies/"+fileName+".mid"));
        if(!mp.isPlaying()){
            mp.start();
        }
        else{
            mp.pause();
            mp.release();
            mp = null;
        }

    }



    private String getFeeling(String fileName){
        if(fileName.contains("H"))
            return "Happy";
        if(fileName.contains("S"))
            return "Sad";
        if(fileName.contains("A"))
            return "Angry";
        if(fileName.contains("N"))
            return "Neutral";
        return "No Feeling";
    }


    private String getFileNameFromDB(String date){
        String fileName ="";
        boolean diaryExist = true;
        try {

            realm = Realm.getDefaultInstance();
            Log.d("date",date);
            final RealmResults<DiaryMetadata> names = realm.where(DiaryMetadata.class).contains("fileName", date).findAll();
            fileName = names.get(0).getFileName();

            Log.d("fileName",fileName);
            melodyPlay(fileName);
        }
        catch(Exception e){
            diaryExist = false;
        }
        finally{
            realm.close();


            if(diaryExist){
                return date + ": " + getFeeling(fileName);
            }
            else{
                return date+": No diary";
            }

        }
    }



}
