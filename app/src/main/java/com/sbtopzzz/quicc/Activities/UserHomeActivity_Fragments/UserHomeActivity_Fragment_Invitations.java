package com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.API.Schemas.Event;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Home_Objects.MyEvent;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Home_Objects.MyEventsAdapter;
import com.sbtopzzz.quicc.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserHomeActivity_Fragment_Invitations extends Fragment {
    private Context context;
    private RecyclerView rvInvitations;

    public UserHomeActivity_Fragment_Invitations(@NonNull Context context) {
        this.context = context;
    }

    public static UserHomeActivity_Fragment_Invitations newInstance(@NonNull Context context) {
        UserHomeActivity_Fragment_Invitations fragment = new UserHomeActivity_Fragment_Invitations(context);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.fragment_user_home_activity___invitations, container, false);
        Initialize(main);
        GetInvitations();

        return main;
    }

    private void Initialize(View parent) {
        rvInvitations = parent.findViewById(R.id.rvInvitations);
    }

    private void GetInvitations() {
        Funcs.eventsInvited(CurrentUser.user.uid, new Funcs.EventsInvitedResult() {
            @Override
            public void onSuccess(@NonNull List<Event> invitedEvents) {
                Toast.makeText(context, "Events count: " + invitedEvents.size(), Toast.LENGTH_SHORT).show();

                List<MyEvent> myEvents = new ArrayList<>();
                for (Event event : invitedEvents)
                    myEvents.add(new MyEvent(event.getTitle(), new Date(event.getStartDate()), event.uid));

                MyEventsAdapter adapter = new MyEventsAdapter(context, myEvents);
                rvInvitations.setHasFixedSize(true);
                rvInvitations.setLayoutManager(new LinearLayoutManager(context));
                rvInvitations.setAdapter(adapter);
            }

            @Override
            public void onWarning(String errorText) {
                Toast.makeText(context, "Warning: " + errorText, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                Toast.makeText(context, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}