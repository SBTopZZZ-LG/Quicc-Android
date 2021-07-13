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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.API.Schemas.Event;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UserEditEventActivity extends AppCompatActivity {
    private String eventUid;

    private TextView tvEventStart, tvEventEnd;
    private EditText etEventTitle;
    private Button btnEventStart, btnEventEnd, btnEditEvent;

    private long startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_event);
        Initialize();
        LoadEventDetails();

        btnEventStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(true);
            }
        });
        btnEventEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(false);
            }
        });

        btnEditEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Funcs.eventGet(eventUid, new Funcs.EventGetResult() {
                    @Override
                    public void onSuccess(@NonNull Event event) {
                        event.setTitle(etEventTitle.getText().toString());
                        event.setStartDate(startDate);
                        event.setEndDate(endDate);

                        Funcs.eventUpdate(CurrentUser.user.getEmailId(), eventUid, event, new Funcs.EventUpdateResult() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(UserEditEventActivity.this, "Changes were saved", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onWarning(String errorText) {
                                Toast.makeText(UserEditEventActivity.this, "Warning: " + errorText, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(@NonNull Throwable t) {
                                Toast.makeText(UserEditEventActivity.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onWarning(String errorText) {

                    }

                    @Override
                    public void onFailure(@NonNull Throwable t) {

                    }
                });
            }
        });
    }

    private void Initialize() {
        eventUid = getIntent().getExtras().getString("eventUid");

        tvEventStart = findViewById(R.id.tvEventStart);
        tvEventEnd = findViewById(R.id.tvEventEnd);

        etEventTitle = findViewById(R.id.etEventTitle);

        btnEventStart = findViewById(R.id.btnSelectEventStart);
        btnEventEnd = findViewById(R.id.btnSelectEventEnd);

        btnEditEvent = findViewById(R.id.btnConfirmChanges);
    }

    private void LoadEventDetails() {
        Funcs.eventGet(eventUid, new Funcs.EventGetResult() {
            @Override
            public void onSuccess(@NonNull Event event) {
                etEventTitle.setText(event.getTitle());

                startDate = event.getStartDate();
                endDate = event.getEndDate();

                tvEventStart.setText(new SimpleDateFormat("dd/MM, hh:mm a").format(new Date(startDate)));
                tvEventEnd.setText(new SimpleDateFormat("dd/MM, hh:mm a").format(new Date(endDate)));
            }

            @Override
            public void onWarning(String errorText) {
                Toast.makeText(UserEditEventActivity.this, "Warning: " + errorText, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                Toast.makeText(UserEditEventActivity.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void pickDate(boolean isStartDate){
        final Calendar c = Calendar.getInstance();

        if (isStartDate)
            c.setTimeInMillis(startDate);
        else
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
                        TimePickerDialog timePickerDialog = new TimePickerDialog(UserEditEventActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        c.set(year, monthOfYear, dayOfMonth, hourOfDay, minute);

                                        if (isStartDate) {
                                            startDate = c.getTimeInMillis();

                                            tvEventStart.setText(new SimpleDateFormat("dd/MM, hh:mm a").format(new Date(startDate)));

                                            Toast.makeText(UserEditEventActivity.this, String.valueOf(startDate), Toast.LENGTH_SHORT).show();
                                        } else {
                                            endDate = c.getTimeInMillis();

                                            tvEventEnd.setText(new SimpleDateFormat("dd/MM, hh:mm a").format(new Date(endDate)));

                                            Toast.makeText(UserEditEventActivity.this, String.valueOf(endDate), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}