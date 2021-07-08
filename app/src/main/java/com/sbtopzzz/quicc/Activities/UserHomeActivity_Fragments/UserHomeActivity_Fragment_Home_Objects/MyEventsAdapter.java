package com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Home_Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sbtopzzz.quicc.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.ViewHolder> {
    private Context context;
    private List<MyEvent> events;

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

        Date startDate = myEvent.getStartDate();
        holder.tvEventStart.setText("Starting on " + new SimpleDateFormat("d/M").format(startDate) + ", " + new SimpleDateFormat("hh:mm a").format(startDate));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEventName, tvEventStart;

        public ViewHolder(View itemView) {
            super(itemView);

            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvEventStart = itemView.findViewById(R.id.tvEventStart);
        }
    }
}
