package com.sbtopzzz.quicc.Activities.UserViewEventActivity_Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sbtopzzz.quicc.API.Schemas.Event;
import com.sbtopzzz.quicc.R;

import java.util.List;

public class InviteesAdapter extends RecyclerView.Adapter<InviteesAdapter.ViewHolder> {
    private Event event;
    private List<Invitee> invitees;

    public InviteesAdapter(List<Invitee> invitees, Event event) {
        this.invitees = invitees;
        this.event = event;
    }

    @NonNull
    @Override
    public InviteesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.uservieweventactivity_invitees_tile1, parent, false);
        InviteesAdapter.ViewHolder viewHolder = new InviteesAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InviteesAdapter.ViewHolder holder, int position) {
        final Invitee invitee = invitees.get(position);

        holder.tvUserName.setText(invitee.getUserName());
        holder.tvEmailID.setText(invitee.getEmailId());

        if (event.getVisitedMembers().contains(invitee.uid))
            holder.cvJoinedStatus.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return invitees.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName, tvEmailID;
        public CardView cvJoinedStatus;

        public ViewHolder(View itemView) {
            super(itemView);

            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvEmailID = itemView.findViewById(R.id.tvEmailID);
            cvJoinedStatus = itemView.findViewById(R.id.cvJoinedStatus);
        }
    }
}
