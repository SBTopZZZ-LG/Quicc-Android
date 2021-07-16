package com.sbtopzzz.quicc.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.API.Schemas.Event;
import com.sbtopzzz.quicc.API.Schemas.User;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.R;

import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    private TextView tvUserName, tvEmailID, tvEventsCount, tvInvitedEvents, tvEventsAttended;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Initialize();
        LoadInformation();
    }

    private void LoadInformation() {
        Funcs.userGetByEmail(CurrentUser.user.getEmailId(), CurrentUser.user.getEmailId(), new Funcs.UserGetResult() {
            @Override
            public void onSuccess(@NonNull User user) {
                CurrentUser.user = user;

                tvUserName.setText(CurrentUser.user.getName());
                tvEmailID.setText(CurrentUser.user.getEmailId());

                Funcs.eventsGet(CurrentUser.user.uid, new Funcs.EventsGetResult() {
                    @Override
                    public void onSuccess(@NonNull List<Event> events) {
                        tvEventsCount.setText(String.valueOf(events.size()));

                        Funcs.eventsInvited(CurrentUser.user.uid, new Funcs.EventsInvitedResult() {
                            @Override
                            public void onSuccess(@NonNull List<Event> invitedEvents) {
                                tvInvitedEvents.setText(String.valueOf(invitedEvents.size()));

                                int count = 0;
                                for (int i = 0; i < invitedEvents.size(); i++)
                                    count += (invitedEvents.get(i).getVisitedMembers().contains(user.uid) ? 1 : 0);

                                tvEventsAttended.setText(String.valueOf(count));
                            }

                            @Override
                            public void onWarning(String errorText) {

                            }

                            @Override
                            public void onFailure(@NonNull Throwable t) {

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

            @Override
            public void onWarning(String errorText) {

            }

            @Override
            public void onFailure(@NonNull Throwable t) {

            }
        });
    }

    private void Initialize() {
        tvUserName = findViewById(R.id.tvUserName);
        tvEmailID = findViewById(R.id.tvEmailID);

        tvEventsCount = findViewById(R.id.tvEventsCount);
        tvInvitedEvents = findViewById(R.id.tvInvitedEvents);
        tvEventsAttended = findViewById(R.id.tvEventsAttended);

        tvUserName.setText(CurrentUser.user.getName());
        tvEmailID.setText(CurrentUser.user.getEmailId());
    }
}