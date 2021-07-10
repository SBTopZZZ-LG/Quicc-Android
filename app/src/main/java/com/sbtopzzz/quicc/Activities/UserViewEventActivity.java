package com.sbtopzzz.quicc.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.API.Schemas.Event;
import com.sbtopzzz.quicc.API.Schemas.User;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Home_Objects.MyEventsAdapter;
import com.sbtopzzz.quicc.Activities.UserViewEventActivity_Objects.Invitee;
import com.sbtopzzz.quicc.Activities.UserViewEventActivity_Objects.InviteesAdapter;
import com.sbtopzzz.quicc.Activities.UserViewEventActivity_Objects.Organizer;
import com.sbtopzzz.quicc.Activities.UserViewEventActivity_Objects.OrganizersAdapter;
import com.sbtopzzz.quicc.R;

import java.util.ArrayList;
import java.util.List;

public class UserViewEventActivity extends AppCompatActivity {
    private String eventUid;

    private TextView tvEventName;
    private RecyclerView rvOrganizers, rvInvitees;
    private TextView tvEventStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_event);
        Initialize();

    }

    private void Initialize() {
        eventUid = getIntent().getExtras().getString("eventUid");

        tvEventName = findViewById(R.id.tvEventName);

        rvOrganizers = findViewById(R.id.rvOrganizers);
        rvInvitees = findViewById(R.id.rvInvitees);

        tvEventStart = findViewById(R.id.tvEventStart);

        LoadEvent();
    }

    private void LoadEvent() {
        Funcs.eventGet(eventUid, new Funcs.EventGetResult() {
            @Override
            public void onSuccess(@NonNull Event event) {
                tvEventName.setText(event.getTitle());

                OrganizersAdapter adapter = new OrganizersAdapter(UserViewEventActivity.this,
                        new ArrayList<Organizer>() {{add(new Organizer(event.getHost(), "HOST"));}});
                rvOrganizers.setHasFixedSize(true);
                rvOrganizers.setLayoutManager(new LinearLayoutManager(UserViewEventActivity.this));
                rvOrganizers.setAdapter(adapter);

                List<Invitee> invitees = new ArrayList<>();

                InviteesAdapter adapter2 = new InviteesAdapter(UserViewEventActivity.this, invitees);
                rvInvitees.setHasFixedSize(true);
                rvInvitees.setLayoutManager(new LinearLayoutManager(UserViewEventActivity.this));
                rvInvitees.setAdapter(adapter2);

                for (String inviteeUid : event.getMembers())
                    Funcs.userGetByUid(CurrentUser.user.getEmailId(), inviteeUid, new Funcs.UserGetResult() {
                        @Override
                        public void onSuccess(@NonNull User user) {
                            invitees.add(new Invitee(user.getName(), user.getEmailId()));

                            adapter2.notifyDataSetChanged();
                        }

                        @Override
                        public void onWarning(String errorText) {
                            Toast.makeText(UserViewEventActivity.this, "Warning: " + errorText, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(@NonNull Throwable t) {
                            Toast.makeText(UserViewEventActivity.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                tvEventStart.setText(event.getStartDate().toString());
            }

            @Override
            public void onWarning(String errorText) {
                Toast.makeText(UserViewEventActivity.this, "Main Warning: " + errorText, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                Toast.makeText(UserViewEventActivity.this, "Main Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}