package com.sbtopzzz.quicc.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.API.Schemas.Event;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.R;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class UserCreateEventActivity extends AppCompatActivity {
    private EditText etEventTitle;
    private Button btnStartDate, btnEndDate, btnCreateEvent;

    private long startDate = -1;
    private long endDate = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create_event);
        Initialize();

        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateEvent();
            }
        });
    }

    private void CreateEvent() {
        Event e = new Event(etEventTitle.getText().toString(),
                startDate, endDate);
        Funcs.eventCreate(CurrentUser.user.getEmailId(), e, new Funcs.EventCreateResult() {
            @Override
            public void onSuccess(@NonNull String eventUid) {
                Toast.makeText(UserCreateEventActivity.this, "Event created! Uid: " + eventUid, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onWarning(String errorText) {
                Toast.makeText(UserCreateEventActivity.this, "Warning: " + errorText, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                Toast.makeText(UserCreateEventActivity.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Initialize() {
        etEventTitle = findViewById(R.id.etEventTitle);

        btnStartDate = findViewById(R.id.btnSelectEventStart);
        btnEndDate = findViewById(R.id.btnSelectEventEnd);

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(true);
            }
        });
        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(false);
            }
        });

        btnCreateEvent = findViewById(R.id.btnCreateEvent);
    }

    private void pickDate(boolean isStartDate){
        final Calendar c = Calendar.getInstance();

        if (isStartDate)
            if (startDate != -1)
                c.setTimeInMillis(startDate);
            else {}
            else
                if (endDate != -1)
                    c.setTimeInMillis(endDate);

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int mHour = c.get(Calendar.HOUR_OF_DAY);
                        int mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(UserCreateEventActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        c.set(year, monthOfYear, dayOfMonth, hourOfDay, minute);

                                        if (isStartDate) {
                                            startDate = c.getTimeInMillis();

                                            Toast.makeText(UserCreateEventActivity.this, String.valueOf(startDate), Toast.LENGTH_SHORT).show();
                                        } else {
                                            endDate = c.getTimeInMillis();

                                            Toast.makeText(UserCreateEventActivity.this, String.valueOf(endDate), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}