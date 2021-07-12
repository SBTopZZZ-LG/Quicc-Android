package com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Home_Objects;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sbtopzzz.quicc.Activities.UserViewEventActivity;
import com.sbtopzzz.quicc.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.ViewHolder> {
    private Context context;
    private List<MyEvent> events;

    private PrettyTime p = new PrettyTime();

    public MyEventsAdapter(@NonNull Context context, List<MyEvent> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.userhomeactivity_home_myevents_tile1, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventsAdapter.ViewHolder holder, int position) {
        final MyEvent myEvent = events.get(position);
        holder.tvEventName.setText(myEvent.getEventName());

        Date today = new Date();
        Date startDate = myEvent.getStartDate();
        Date endDate = myEvent.getEndDate();
        if (today.after(startDate)) {
            if (today.before(endDate)) {
                // Event has started
                holder.tvEventStart.setText("Started " + p.format(startDate));
            } else {
                // Event has ended
                holder.tvEventStart.setText("Ended " + p.format(endDate));
            }
        } else
            holder.tvEventStart.setText("Will start " + p.format(startDate));

        holder.clMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, UserViewEventActivity.class)
                .putExtra("eventUid", myEvent.getEventUid()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout clMain;
        public TextView tvEventName, tvEventStart;

        public ViewHolder( View itemView) {
            super(itemView);

            clMain = itemView.findViewById(R.id.clMain);
            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvEventStart = itemView.findViewById(R.id.tvEventStart);
        }
    }
}
