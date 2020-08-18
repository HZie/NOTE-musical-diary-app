package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;


import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;


public class CalendarActivity extends AppCompatActivity
        implements OnDateSelectedListener, OnMonthChangedListener, OnDateLongClickListener {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
    public static final CharSequence[] ITEMS =
            new CharSequence[] { "EDIT", "SINGLE", "MULTIPLE", "RANGE" };

 //   @BindView(R.id.calendarView)
    //  MaterialCalendarView widget;

  //  @BindView(R.id.textView)
 //   TextView textView;

    private ImageView main;
    private  ImageView calendar;
    private ImageView stars;
    private ImageView settings;

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
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
    }

    @OnCheckedChanged(R.id.calendar_mode)
    void onCalendarModeChanged(boolean checked) {
        MaterialCalendarView widget = (MaterialCalendarView)findViewById(R.id.calendarView);
      //  TextView textView = (TextView)findViewById(R.id.textView);
        final CalendarMode mode = checked ? CalendarMode.WEEKS : CalendarMode.MONTHS;
        widget.state().edit().setCalendarDisplayMode(mode).commit();
    }

//    @OnClick(R.id.button_selection_mode) void onChangeSelectionMode() {
//        new AlertDialog.Builder(this)
//                .setTitle("Selection Mode")
//                .setSingleChoiceItems(ITEMS, widget.getSelectionMode(), (dialog, which) -> {
//                    widget.setSelectionMode(which);
//                    dialog.dismiss();
//                })
//                .show();
//    }

    @OnClick(R.id.button_selection_mode) void onChangeSelectionMode() {

        MaterialCalendarView widget = (MaterialCalendarView)findViewById(R.id.calendarView);
       // TextView textView = (TextView)findViewById(R.id.textView);
        new AlertDialog.Builder(this)
                .setTitle("Selection Mode")
                .setSingleChoiceItems(ITEMS, widget.getSelectionMode(),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MaterialCalendarView widget = (MaterialCalendarView)findViewById(R.id.calendarView);
                       // TextView textView = (TextView)findViewById(R.id.textView);
                        // do something here
                        widget.setSelectionMode(which);
                        dialog.dismiss();
                    }
                })
                .show();
    }



    @OnClick(R.id.get_selected_dates) public void getSelectedDateClick(final View v) {
        MaterialCalendarView widget = (MaterialCalendarView)findViewById(R.id.calendarView);
        // TextView textView = (TextView)findViewById(R.id.textView);
        final List<CalendarDay> selectedDates = widget.getSelectedDates();
        if (!selectedDates.isEmpty()) {
            Toast.makeText(this, selectedDates.toString(), Toast.LENGTH_SHORT).show();
            Log.e("GettersActivity", selectedDates.toString());
        } else {
            Toast.makeText(this, "No Selection", Toast.LENGTH_SHORT).show();
        }
    }



}
