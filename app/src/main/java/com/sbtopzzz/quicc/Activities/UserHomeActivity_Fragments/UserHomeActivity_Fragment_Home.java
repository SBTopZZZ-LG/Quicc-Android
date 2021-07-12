package com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.API.Schemas.Event;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Home_Objects.MyEvent;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Home_Objects.MyEventsAdapter;
import com.sbtopzzz.quicc.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserHomeActivity_Fragment_Home extends Fragment {
    private Context context;
    private RecyclerView rvMyEvents;

    public UserHomeActivity_Fragment_Home(@NonNull Context context) {
        this.context = context;
    }

    public static UserHomeActivity_Fragment_Home newInstance(@NonNull Context context) {
        UserHomeActivity_Fragment_Home fragment = new UserHomeActivity_Fragment_Home(context);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.fragment_user_home_activity___home, container, false);
        Initialize(main);
        GetMyEvents();

        return main;
    }

    private void Initialize(View parent) {
        rvMyEvents = parent.findViewById(R.id.rvMyEvents);
    }

    private void GetMyEvents() {
        Funcs.eventsGet(CurrentUser.user.uid, new Funcs.EventsGetResult() {
            @Override
            public void onSuccess(@NonNull List<Event> events) {
                Toast.makeText(context, "Events count: " + events.size(), Toast.LENGTH_SHORT).show();

                List<MyEvent> myEvents = new ArrayList<>();
                for (Event event : events)
                    myEvents.add(new MyEvent(event.getTitle(), new Date(event.getStartDate()), new Date(event.getEndDate()), event.uid));

                MyEventsAdapter adapter = new MyEventsAdapter(context, myEvents);
                rvMyEvents.setHasFixedSize(true);
                rvMyEvents.setLayoutManager(new LinearLayoutManager(context));
                rvMyEvents.setAdapter(adapter);
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